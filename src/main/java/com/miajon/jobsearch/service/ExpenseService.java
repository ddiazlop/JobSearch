package com.miajon.jobsearch.service;

import com.miajon.jobsearch.model.Expense;
import com.miajon.jobsearch.records.ExpenseRecords.ExpenseByMonth;
import com.miajon.jobsearch.records.ExpenseRecords.ExpensesByType;
import com.miajon.jobsearch.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @SuppressWarnings("null")
    public void saveExpense(Expense expense) {
        this.expenseRepository.save(expense);
    }

    public List<Expense> findAllExpenses() {
        return this.expenseRepository.findAll();
    }

    @SuppressWarnings("null")
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

    public List<Expense> findLastTenExpenses() {
        return this.expenseRepository.findLastTenExpenses();
    }

    public List<ExpenseByMonth> getIncomePerMonth() {
        return this.expenseRepository.getIncomePerMonth();
    }

    public List<ExpenseByMonth> getExpensesPerMonth() {
        return this.expenseRepository.getExpensesPerMonth();
    }

    public List<ExpensesByType> getAmountsByType() {
        return this.expenseRepository.getAmountsByType();
    }

}
