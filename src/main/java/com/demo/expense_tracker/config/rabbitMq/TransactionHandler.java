package com.demo.expense_tracker.config.rabbitMq;

public interface TransactionHandler {
    void handle(Transaction transaction);
}
