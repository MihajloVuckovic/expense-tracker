package com.demo.expense_tracker.config.rabbitMq;

import com.demo.expense_tracker.model.Expense;
import com.demo.expense_tracker.model.Income;
import com.demo.expense_tracker.services.ExpenseService;
import com.demo.expense_tracker.services.IncomeService;

import com.demo.expense_tracker.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TransactionListener {
    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private TransactionHandlerFactory transactionHandlerFactory;

    @Transactional
    @RabbitListener(queues = "q.transactions")
    public void handleTransaction(Transaction transaction) {
        validateTransaction(transaction);
        TransactionHandler handler = transactionHandlerFactory.getHandler(transaction.getType());

        if(handler != null){
            handler.handle(transaction);
        }else{
            throw new RabbitMQException("Unknown transaction type: " + transaction.getType());
        }
    }

    private void validateTransaction(Transaction transaction){
        if(transaction.getUser_id() == null){
            throw new RabbitMQException("User id is null");
        }
    }
}
