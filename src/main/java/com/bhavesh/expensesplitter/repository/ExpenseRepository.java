package com.bhavesh.expensesplitter.repository;

import com.bhavesh.expensesplitter.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExpenseRepository extends JpaRepository<Expense, Long>{

}
