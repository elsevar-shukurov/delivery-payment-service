package com.example.mspayment.consumer;

import com.example.mspayment.dto.PaymentRequestDto;
import com.example.mspayment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import static com.example.mspayment.config.RabbitMQConfig.ORDER_CREATED_PAYMENT_QUEUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = ORDER_CREATED_PAYMENT_QUEUE)
    public void handleOrderCreated(String message) {
        log.info("PaymentConsumer.handleOrderCreated.start message: {}", message);
        var requestDto = objectMapper.readValue(message, PaymentRequestDto.class);
        paymentService.createPayment(requestDto);
        log.info("PaymentConsumer.handleOrderCreated.end");
    }
}
