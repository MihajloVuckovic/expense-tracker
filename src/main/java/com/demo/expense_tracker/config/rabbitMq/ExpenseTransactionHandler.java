package com.demo.expense_tracker.config.rabbitMq;

import com.demo.expense_tracker.model.Expense;
import com.demo.expense_tracker.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;

public class ExpenseTransactionHandler implements TransactionHandler {

    private final ExpenseService expenseService;

    public ExpenseTransactionHandler(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Override
    public void handle(Transaction transaction) {
        Expense expense = new Expense();
        expense.setAmount(transaction.getAmount());
        expense.setDescription(transaction.getDescription());
        expense.setExpense_group_id(transaction.getGroup_id());
        expense.setUser_id(transaction.getUser_id());
        expenseService.save(expense);
    }
}
