package com.bhavesh.expensesplitter.controller;

import com.bhavesh.expensesplitter.model.Expense;
import com.bhavesh.expensesplitter.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @PostMapping
    public Expense createExpense(@Valid @RequestBody Expense expense) {
        return expenseService.saveExpense(expense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @Valid @RequestBody Expense expense) {
        return expenseService.getExpense(id)
                .map(existing -> {
                    existing.setAmount(expense.getAmount());
                    existing.setDescription(expense.getDescription());
                    existing.setPaidBy(expense.getPaidBy());
                    existing.setSplitType(expense.getSplitType());
                    existing.setShares(expense.getShares());
                    return ResponseEntity.ok(expenseService.saveExpense(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        return expenseService.getExpense(id)
                .map(expense -> {
                    expenseService.deleteExpense(id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
