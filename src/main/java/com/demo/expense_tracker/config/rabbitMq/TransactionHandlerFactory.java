package com.demo.expense_tracker.config.rabbitMq;

import com.demo.expense_tracker.services.ExpenseService;
import com.demo.expense_tracker.services.IncomeService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TransactionHandlerFactory {
    private final Map<String, TransactionHandler> handlers = new HashMap<>();

    public TransactionHandlerFactory(ExpenseService expenseService, IncomeService incomeService){
        handlers.put("expense", new ExpenseTransactionHandler(expenseService));
        handlers.put("income", new IncomeTransactionHandler(incomeService));
    }

    public TransactionHandler getHandler(String type){
        return handlers.get(type.toLowerCase());
    }
}
