package com.demo.expense_tracker.config.rabbitMq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendTransaction(Transaction transaction) {
        rabbitTemplate.convertAndSend("", "q.transactions", transaction);
        log.info("Transaction sent: {}", transaction);
    }
}
