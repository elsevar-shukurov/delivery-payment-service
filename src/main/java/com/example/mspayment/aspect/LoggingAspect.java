package com.example.mspayment.aspect;

import com.example.mspayment.annotation.Loggable;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("@annotation(loggable) || @within(loggable)")
    public Object logAround(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("START: {}.{}() - Arguments: {}", className, methodName, args);

        try {
            Object result = joinPoint.proceed();

            log.info("END: {}.{}() - Returned: {}", className, methodName, result);
            return result;
        } catch (Throwable exception) {
            log.error("ERROR: {}.{}() - Exception: {}", className, methodName, exception.getMessage());
            throw exception;
        }
    }
}