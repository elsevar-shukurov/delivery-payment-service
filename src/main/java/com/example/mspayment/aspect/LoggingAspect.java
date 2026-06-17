package com.example.mspayment.aspect;

import com.example.mspayment.annotation.Loggable;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("@annotation(loggable) || @within(loggable)")
    public void logBefore(JoinPoint joinPoint, Loggable loggable) {
        log.info("START: {}.{}() - Arguments: {}",
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "@annotation(loggable) || @within(loggable)", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Loggable loggable, Object result) {
        log.info("END: {}.{}() - Returned: {}",
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(),
                result);
    }

    @AfterThrowing(pointcut = "@annotation(loggable) || @within(loggable)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Loggable loggable, Throwable exception) {
        log.error("ERROR: {}.{}() - Exception: {}",
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(),
                exception.getMessage());
    }
}