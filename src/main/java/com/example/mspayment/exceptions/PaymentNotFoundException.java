package com.example.mspayment.exceptions;

public class PaymentNotFoundException extends RuntimeException{
    public PaymentNotFoundException(Long orderId){
        super("Payment not found for orderId: "+ orderId);
    }
}
