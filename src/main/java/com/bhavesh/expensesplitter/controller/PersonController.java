package com.bhavesh.expensesplitter.controller;

import com.bhavesh.expensesplitter.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/people")
public class PersonController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public Set<String> getAllPeople() {
        Set<String> people = new HashSet<>();
        expenseService.getAllExpenses().forEach(expense -> {
            people.add(expense.getPaidBy());
            people.addAll(expense.getShares().keySet());
        });
        return people;
    }
}
