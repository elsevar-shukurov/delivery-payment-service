package com.example.mspayment.consumer;

import com.example.mspayment.dto.PaymentRequestDto;
import com.example.mspayment.events.OrderCreatedEvent;
import com.example.mspayment.events.OrderDeliveredEvent;
import com.example.mspayment.mapper.PaymentMapper;
import com.example.mspayment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import static com.example.mspayment.config.RabbitMQConfig.ORDER_CREATED_PAYMENT_QUEUE;
import static com.example.mspayment.config.RabbitMQConfig.ORDER_DELIVERED_PAYMENT_QUEUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;
    private final PaymentMapper paymentMapper;

    @RabbitListener(queues = ORDER_CREATED_PAYMENT_QUEUE)
    public void handleOrderCreated(String message) {
        try{
            log.info("PaymentConsumer.handleOrderCreated.start message: {}", message);
            var event = objectMapper.readValue(message, OrderCreatedEvent.class);
            paymentService.createPayment(paymentMapper.toPaymentRequestDto(event));
            log.info("PaymentConsumer.handleOrderCreated.end");
        }
        catch (Exception e){
            log.error("PaymentConsumer.handleOrderCreated.fail", e);
        }
    }

    @RabbitListener(queues = ORDER_DELIVERED_PAYMENT_QUEUE)
    public void handleOrderDelivered(String message) {
        try{
            log.info("PaymentConsumer.handleOrderDelivered.start message: {}", message);
            var event = objectMapper.readValue(message, OrderDeliveredEvent.class);
            paymentService.deliverOrder(event.getOrderId(), event.getCourierId());
            log.info("PaymentConsumer.handleOrderDelivered.end");
        }
        catch (Exception e){
            log.error("PaymentConsumer.handleOrderDelivered.fail", e);
        }
    }
}
