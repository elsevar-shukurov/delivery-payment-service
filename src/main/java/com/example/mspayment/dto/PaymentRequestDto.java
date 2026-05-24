package com.example.mspayment.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestDto {
    private Long orderId;
    private Long courierId;
    private BigDecimal deliveryFee;
}
