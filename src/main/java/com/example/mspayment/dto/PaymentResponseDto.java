package com.example.mspayment.dto;

import com.example.mspayment.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(NON_NULL)
public class PaymentResponseDto {
    private Long id;
    private Long orderId;
    private Long courierId;
    private BigDecimal deliveryFee;
    private BigDecimal courierEarning;
    private PaymentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
