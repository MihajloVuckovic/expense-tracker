package com.demo.expense_tracker.config.rabbitMq;

import com.demo.expense_tracker.utils.TokenUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private RabbitMQSender rabbitMQSender;

    @PostMapping
    public void createTransaction(@RequestBody Transaction transaction) {
        rabbitMQSender.sendTransaction(transaction);
    }
}
