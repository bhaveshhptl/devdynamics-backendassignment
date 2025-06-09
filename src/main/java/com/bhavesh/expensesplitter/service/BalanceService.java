package com.bhavesh.expensesplitter.service;

import com.bhavesh.expensesplitter.model.Expense;
import com.bhavesh.expensesplitter.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class BalanceService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public Map<String, BigDecimal> calculateBalances() {
        Map<String, BigDecimal> balances = new HashMap<>();
        for (Expense expense : expenseRepository.findAll()) {
            String payer = expense.getPaidBy();
            BigDecimal total = expense.getAmount();

            // Add to payer
            balances.put(payer, balances.getOrDefault(payer, BigDecimal.ZERO).add(total));

            // Subtract from each participant
            for (var entry : expense.getShares().entrySet()) {
                balances.put(entry.getKey(),
                        balances.getOrDefault(entry.getKey(), BigDecimal.ZERO).subtract(entry.getValue()));
            }
        }
        return balances;
    }
}
