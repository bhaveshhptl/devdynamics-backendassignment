package com.bhavesh.expensesplitter.service;

import com.bhavesh.expensesplitter.model.Expense;
import com.bhavesh.expensesplitter.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense saveExpense(Expense expense) {
        validateSplit(expense);
        return expenseRepository.save(expense);
    }

    private void validateSplit(Expense expense) {
        BigDecimal amount = expense.getAmount();
        Map<String, BigDecimal> shares = expense.getShares();

        switch (expense.getSplitType()) {
            case EQUAL -> {
                BigDecimal expectedShare = amount.divide(
                        BigDecimal.valueOf(shares.size()), 2, RoundingMode.HALF_UP
                );
                shares.values().forEach(share -> {
                    if (share.compareTo(expectedShare) != 0) {
                        throw new IllegalArgumentException(
                                "Equal split shares must all be " + expectedShare
                        );
                    }
                });
            }
            case PERCENTAGE -> {
                BigDecimal totalPercentage = shares.values().stream()
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                if (totalPercentage.compareTo(BigDecimal.valueOf(100)) != 0) {
                    throw new IllegalArgumentException(
                            "Total percentage must equal 100"
                    );
                }
            }
            case EXACT -> {
                BigDecimal totalExact = shares.values().stream()
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                if (totalExact.compareTo(amount) != 0) {
                    throw new IllegalArgumentException(
                            "Exact split total must equal expense amount"
                    );
                }
            }
        }
    }

    public Optional<Expense> getExpense(Long id) {
        return expenseRepository.findById(id);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}
