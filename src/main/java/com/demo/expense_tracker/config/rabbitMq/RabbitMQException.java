package com.demo.expense_tracker.config.rabbitMq;

import com.rabbitmq.client.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
public class RabbitMQException extends RuntimeException {
    public RabbitMQException(String msg){
        log.info(msg);
    }
}
