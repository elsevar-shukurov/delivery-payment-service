package com.example.mspayment.exceptions;

public class CourierBalanceNotFoundException extends RuntimeException{
    public CourierBalanceNotFoundException(Long courierId){
        super("Courier balance not found for courierId: "+ courierId);
    }
}
