package com.demo.expense_tracker.config.rabbitMq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendTransaction(Transaction transaction) {
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.EXCHANGE, RabbitMqConfiguration.ROUTING_KEY, transaction);
        System.out.println("message sent" + transaction);
    }
}
