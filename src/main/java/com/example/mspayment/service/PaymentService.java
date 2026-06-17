package com.example.mspayment.service;

import com.example.mspayment.annotation.Loggable;
import com.example.mspayment.dao.entity.Payment;
import com.example.mspayment.dao.repository.CourierBalanceRepository;
import com.example.mspayment.dao.repository.PaymentRepository;
import com.example.mspayment.dto.CourierBalanceResponseDto;
import com.example.mspayment.dto.PaymentRequestDto;
import com.example.mspayment.dto.PaymentResponseDto;
import com.example.mspayment.exceptions.CourierBalanceNotFoundException;
import com.example.mspayment.exceptions.PaymentNotFoundException;
import com.example.mspayment.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.mspayment.mapper.CourierBalanceMapper.toNewEntity;
import static com.example.mspayment.mapper.CourierBalanceMapper.toResponseDto;
import static com.example.mspayment.mapper.PaymentMapper.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Loggable
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final CourierBalanceRepository courierBalanceRepository;

    @Transactional
    public void createPayment(PaymentRequestDto requestDto) {
        if (paymentRepository.existsByOrderId(requestDto.getOrderId())) {
            log.info("Payment already exists for orderId: {}, skipping", requestDto.getOrderId());
            return;
        }
        var courierBalance = courierBalanceRepository
                .findByCourierId(requestDto.getCourierId())
                .orElseGet(() -> courierBalanceRepository.save(
                        toNewEntity(requestDto.getCourierId() ) ) );

        var payment = toEntity(requestDto);
        payment.setCourierBalance(courierBalance);
        paymentRepository.save(payment);
    }


    public List<PaymentResponseDto> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(p-> toPaymentResponseDto(p))
                .toList();
    }

    public PaymentResponseDto getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .map(p->toPaymentResponseDto(p))
                .orElseThrow(() -> new PaymentNotFoundException(orderId));
    }

    public List<PaymentResponseDto> getPaymentsByCourierId(Long courierId) {
        return paymentRepository.findAllByCourierId(courierId)
                .stream()
                .map(p->toPaymentResponseDto(p))
                .toList();
    }

    public CourierBalanceResponseDto getCourierBalance(Long courierId) {
        return courierBalanceRepository.findByCourierId(courierId)
                .map(c->toResponseDto(c))
                .orElseThrow(() -> new CourierBalanceNotFoundException(courierId));
    }
}
