package com.example.mspayment.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handlePaymentNotFound(PaymentNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(CourierBalanceNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleCourierBalanceNotFound(CourierBalanceNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
