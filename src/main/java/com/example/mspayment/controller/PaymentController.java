package com.example.mspayment.controller;

import com.example.mspayment.dao.entity.Payment;
import com.example.mspayment.dto.CourierBalanceResponseDto;
import com.example.mspayment.dto.PaymentRequestDto;
import com.example.mspayment.dto.PaymentResponseDto;
import com.example.mspayment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")

public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createPayment(@Valid @RequestBody PaymentRequestDto requestDto) {
        paymentService.createPayment(requestDto);
    }

    @GetMapping("/order/{orderId}")
    public PaymentResponseDto getPaymentByOrderId(@PathVariable Long orderId) {
        return paymentService.getPaymentByOrderId(orderId);
    }

    @GetMapping
    public List<PaymentResponseDto> getAllPayments() {
        return  paymentService.getAllPayments();
    }

    @GetMapping("/courier/{courierId}")
    public List<PaymentResponseDto> getPaymentsByCourierId(@PathVariable Long courierId) {
        return paymentService.getPaymentsByCourierId(courierId);
    }

    @GetMapping("/courier/{courierId}/balance")
    public CourierBalanceResponseDto getCourierBalance(@PathVariable Long courierId) {
        return paymentService.getCourierBalance(courierId);
    }
}
