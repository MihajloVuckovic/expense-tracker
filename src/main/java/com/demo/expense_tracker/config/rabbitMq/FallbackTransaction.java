package com.demo.expense_tracker.config.rabbitMq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FallbackTransaction {
    @RabbitListener(queues = "q.fall-back")
    public void onTransactionFailure(Transaction transaction){
      log.info("Executing fallback for a failed transaction {}", transaction);
    }
}
