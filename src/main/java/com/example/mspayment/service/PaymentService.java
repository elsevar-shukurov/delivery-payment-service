package com.example.mspayment.service;

import com.example.mspayment.annotation.Loggable;
import com.example.mspayment.criteria.PageCriteria;
import com.example.mspayment.criteria.PaymentCriteria;
import com.example.mspayment.dao.entity.CourierBalance;
import com.example.mspayment.dao.entity.Payment;
import com.example.mspayment.dao.repository.CourierBalanceRepository;
import com.example.mspayment.dao.repository.PaymentRepository;
import com.example.mspayment.dto.CourierBalanceResponseDto;
import com.example.mspayment.dto.PaymentRequestDto;
import com.example.mspayment.dto.PaymentResponseDto;
import com.example.mspayment.enums.PaymentStatus;
import com.example.mspayment.exceptions.CourierBalanceNotFoundException;
import com.example.mspayment.exceptions.PaymentNotFoundException;
import com.example.mspayment.mapper.CourierBalanceMapper;
import com.example.mspayment.mapper.PaymentMapper;
import com.example.mspayment.specification.PaymentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.mspayment.enums.PaymentStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Loggable
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final CourierBalanceRepository courierBalanceRepository;
    private final PaymentMapper paymentMapper;
    private final CourierBalanceMapper courierBalanceMapper;
    private final RedisService redisService;

    @Transactional
    public void createPayment(PaymentRequestDto requestDto) {
        if (paymentRepository.existsByOrderId(requestDto.getOrderId())) {
            log.info("Payment already exists for orderId: {}, skipping", requestDto.getOrderId());
            return;
        }
        var courierBalance = courierBalanceRepository
                .findByCourierId(requestDto.getCourierId())
                .orElseGet(() -> courierBalanceRepository.save(
                        courierBalanceMapper.toNewEntity(requestDto.getCourierId() ) ) );

        var payment = paymentMapper.toEntity(requestDto);
        payment.setCourierBalance(courierBalance);
        paymentRepository.save(payment);
    }

    @Transactional
    public void deliverOrder(Long orderId, long courierId) {
        var payment=  fetchPaymentByOrderId(orderId);
        var courierBalance = fetchCourierBalanceByCourierId(courierId);
        var earning= calculateCourierEarnings(payment);

        payment.setStatus(DELIVERED);
        payment.setCourierEarning(earning);
        courierBalance.setBalance(courierBalance.getBalance().add(earning));
        courierBalance.setTurnover(courierBalance.getTurnover().add(earning));

        courierBalanceRepository.save(courierBalance);
        paymentRepository.save(payment);

        redisService.deleteKey("payment:orderId:" + orderId);
    }

        public Page<PaymentResponseDto> getPayments(PaymentCriteria paymentCriteria, PageCriteria pageCriteria) {

        PageRequest pageable = PageRequest.of(
                pageCriteria.getPageNumber(),
                pageCriteria.getCount(),
                Sort.by(Sort.Direction.fromString(pageCriteria.getSortDirection().toUpperCase()), pageCriteria.getSortBy())
        );

        return paymentRepository.findAll(new PaymentSpecification(paymentCriteria), pageable)
                .map(p-> paymentMapper.toPaymentResponseDto(p));
    }

    public PaymentResponseDto getPaymentByOrderId(Long orderId) {
        String cacheKey = "payment:orderId:" + orderId;

        var cached = redisService.getValue(cacheKey);
        if (cached!=null) {
            log.info("Cache for orderId: {}", orderId);
            return cached;
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        var dto = paymentMapper.toPaymentResponseDto(fetchPaymentByOrderId(orderId));
        redisService.setValue(cacheKey, dto, 600);

        return dto;
    }

    public List<PaymentResponseDto> getPaymentsByCourierId(Long courierId) {
        return paymentRepository.findAllByCourierId(courierId)
                .stream()
                .map(p->paymentMapper.toPaymentResponseDto(p))
                .toList();
    }

    public CourierBalanceResponseDto getCourierBalance(Long courierId) {
        return courierBalanceRepository.findByCourierId(courierId)
                .map(c->courierBalanceMapper.toResponseDto(c))
                .orElseThrow(() -> new CourierBalanceNotFoundException(courierId));
    }

    private Payment fetchPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException(orderId));
    }

    private CourierBalance fetchCourierBalanceByCourierId(Long courierId) {
        return courierBalanceRepository.findByCourierId(courierId)
                .orElseThrow(() -> new CourierBalanceNotFoundException(courierId));
    }

    private BigDecimal calculateCourierEarnings(Payment payment) {
        return payment.getDeliveryFee().multiply(new BigDecimal("0.8"));
    }

    public int processStalePendingPayments() {
        LocalDateTime cutoff = LocalDateTime.now().minusHours(24);
        List<Payment> stalePayments = paymentRepository.findByStatusAndCreatedAtBefore(PENDING, cutoff);

        if (stalePayments.isEmpty()) {
            log.info("No stale PENDING payments found.");
            return 0;
        }

        stalePayments.forEach(payment -> {
            payment.setStatus(FAILED);
        });

        paymentRepository.saveAll(stalePayments);
        log.info("Updated {} stale PENDING payments to FAILED.", stalePayments.size());
        return stalePayments.size();
    }
}
