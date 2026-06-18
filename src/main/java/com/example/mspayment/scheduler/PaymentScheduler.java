package com.example.mspayment.scheduler;

import com.example.mspayment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class PaymentScheduler {

    private final PaymentService paymentService;

    @Scheduled(cron = "0 0 3 * * ?")
    public void cleanStalePendingPayments() {
        log.info("Starting stale pending payments cleanup...");
        int count = paymentService.processStalePendingPayments();
        log.info("Stale pending payments cleanup completed. Updated {} records.", count);
    }
}
