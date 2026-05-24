package com.example.mspayment.mapper;

import com.example.mspayment.dao.entity.Payment;
import com.example.mspayment.dto.PaymentRequestDto;
import com.example.mspayment.dto.PaymentResponseDto;

import static com.example.mspayment.enums.PaymentStatus.PENDING;

public class PaymentMapper {
    public static Payment toEntity(PaymentRequestDto paymentRequestDto) {
        return Payment.builder()
                .orderId(paymentRequestDto.getOrderId())
                .courierId(paymentRequestDto.getCourierId())
                .deliveryFee(paymentRequestDto.getDeliveryFee())
                .status(PENDING)
                .build();
    }

    public static PaymentResponseDto toPaymentResponseDto(Payment payment) {
        return PaymentResponseDto.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .courierId(payment.getCourierId())
                .deliveryFee(payment.getDeliveryFee())
                .courierEarning(payment.getCourierEarning())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
