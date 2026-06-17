package com.example.mspayment.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_CREATED_PAYMENT_QUEUE = "order.created.payment.queue";
    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String ORDER_CREATED_PAYMENT_KEY = "order.created.payment";

    @Bean
    public Queue orderCreatedPaymentQueue() {
        return QueueBuilder.durable(ORDER_CREATED_PAYMENT_QUEUE).build();
    }

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE);
    }

    @Bean
    public Binding orderCreatedPaymentBinding() {
        return BindingBuilder
                .bind(orderCreatedPaymentQueue())
                .to(orderExchange())
                .with(ORDER_CREATED_PAYMENT_KEY);
    }
}
