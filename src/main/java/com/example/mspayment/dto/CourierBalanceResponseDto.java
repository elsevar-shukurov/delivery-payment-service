package com.example.mspayment.dto;

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
public class CourierBalanceResponseDto {
    private Long id;
    private Long courierId;
    private BigDecimal balance;
    private BigDecimal turnover;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
