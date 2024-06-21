package com.demo.expense_tracker.config.rabbitMq;

import com.demo.expense_tracker.model.Income;
import com.demo.expense_tracker.services.IncomeService;

public class IncomeTransactionHandler implements TransactionHandler{
    private final IncomeService incomeService;

    public IncomeTransactionHandler(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @Override
    public void handle(Transaction transaction) {
        Income income = new Income();
        income.setAmount(transaction.getAmount());
        income.setDescription(transaction.getDescription());
        income.setIncome_group_id(transaction.getGroup_id());
        incomeService.save(income);
    }
}
