package com.example.mspayment.mapper;

import com.example.mspayment.dao.entity.CourierBalance;
import com.example.mspayment.dto.CourierBalanceResponseDto;

import java.math.BigDecimal;

public class CourierBalanceMapper {
    public static CourierBalance toNewEntity(Long courierId) {
        return CourierBalance.builder()
                .courierId(courierId)
                .balance(BigDecimal.ZERO)
                .turnover(BigDecimal.ZERO)
                .build();
    }

    public static CourierBalanceResponseDto toResponseDto(CourierBalance courierBalance) {
        return CourierBalanceResponseDto.builder()
                .id(courierBalance.getId())
                .courierId(courierBalance.getCourierId())
                .balance(courierBalance.getBalance())
                .turnover(courierBalance.getTurnover())
                .createdAt(courierBalance.getCreatedAt())
                .updatedAt(courierBalance.getUpdatedAt())
                .build();
    }
}
