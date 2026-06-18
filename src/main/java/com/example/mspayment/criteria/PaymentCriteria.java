package com.example.mspayment.criteria;

import com.example.mspayment.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentCriteria {
    private Long orderId;
    private Long courierId;
    private PaymentStatus status;
    private BigDecimal minDeliveryFee;
    private BigDecimal maxDeliveryFee;
    private BigDecimal minCourierEarning;
    private BigDecimal maxCourierEarning;
    private LocalDateTime minCreatedAt;
    private LocalDateTime maxCreatedAt;
}
