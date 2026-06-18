package com.example.mspayment.mapper;

import com.example.mspayment.dao.entity.Payment;
import com.example.mspayment.dto.PaymentRequestDto;
import com.example.mspayment.dto.PaymentResponseDto;
import com.example.mspayment.events.OrderCreatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {


    @Mapping(target = "status", ignore = true)
    Payment toEntity(PaymentRequestDto paymentRequestDto);

    PaymentResponseDto toPaymentResponseDto(Payment payment);

    PaymentRequestDto toPaymentRequestDto(OrderCreatedEvent event);
}
