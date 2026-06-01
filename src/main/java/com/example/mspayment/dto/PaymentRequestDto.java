package com.example.mspayment.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestDto {
    @NotNull(message = "Order ID cannot be null")
    private Long orderId;

    @NotNull(message = "Courier ID cannot be null")
    private Long courierId;

    @NotNull(message = "Delivery fee cannot be null")
    @DecimalMin(value = "0.00", inclusive = true, message = "Delivery fee cannot be negative")
    private BigDecimal deliveryFee;
}
