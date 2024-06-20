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

    @Transactional
    @RabbitListener(queues = "q.transactions")
    public void handleTransaction(Transaction transaction) {

            if ("expense".equalsIgnoreCase(transaction.getType())) {
                Expense expense = new Expense();
                expense.setAmount(transaction.getAmount());
                expense.setDescription(transaction.getDescription());
                expense.setExpense_group_id(transaction.getGroup_id());
                if (transaction.getUser_id() == null) {
                    throw new RabbitMQException("User id is null");
                }
                expense.setUser_id(transaction.getUser_id());
                expenseService.save(expense);
            } else if ("income".equalsIgnoreCase(transaction.getType())) {
                Income income = new Income();
                income.setAmount(transaction.getAmount());
                income.setDescription(transaction.getDescription());
                income.setIncome_group_id(transaction.getGroup_id());
                if (transaction.getUser_id() == null) {
                    throw new RabbitMQException("User_id is null");
                }
                income.setUser_id(transaction.getUser_id());
                incomeService.save(income);
            }

    }
}
