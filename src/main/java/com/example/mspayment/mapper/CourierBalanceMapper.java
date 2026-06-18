package com.example.mspayment.mapper;

import com.example.mspayment.dao.entity.CourierBalance;
import com.example.mspayment.dto.CourierBalanceResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourierBalanceMapper {

    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "turnover", ignore = true)
    CourierBalance toNewEntity(Long courierId);

    CourierBalanceResponseDto toResponseDto(CourierBalance courierBalance);
}
