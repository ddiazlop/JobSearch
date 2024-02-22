package com.miajon.jobsearch.service;

import com.miajon.jobsearch.model.Expense;
import com.miajon.jobsearch.records.ExpenseRecords;
import com.miajon.jobsearch.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public void saveExpense(Expense expense) {
        this.expenseRepository.save(expense);
    }

    public List<Expense> findAllExpenses() {
        return this.expenseRepository.findAll();
    }

    public void deleteExpense(String id) {
        this.expenseRepository.deleteById(id);
    }

    public Double getTotalAmount() {
        return this.expenseRepository.getTotalAmount();
    }

    public Iterable<Expense> findMonthlyExpenses() {
        return this.expenseRepository.findMonthlyExpenses(true);
    }

    public Double getTotalMonthlyAmount() {
        return this.expenseRepository.getTotalMonthlyAmount();
    }

    public Iterable<Expense> findAllByOrderByDateDesc() {
        return this.expenseRepository.findAllByOrderByDateDesc();
    }

    public List<ExpenseRecords.ExpensesByMonth> getIncomePerMonth() {
        return this.expenseRepository.getIncomePerMonth();
    }

    public List<Expense> findLastTenExpenses() {
        return this.expenseRepository.findLastTenExpenses();
    }

    public List<ExpenseRecords.ExpensesByMonth> getExpensesPerMonth() {
        return this.expenseRepository.getExpensesPerMonth();
    }

}
