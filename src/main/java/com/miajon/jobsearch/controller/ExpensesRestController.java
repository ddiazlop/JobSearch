package com.miajon.jobsearch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miajon.jobsearch.exception.PredictionException;
import com.miajon.jobsearch.model.Expense;
import com.miajon.jobsearch.model.forms.ExpenseForm;
import com.miajon.jobsearch.records.ExpenseRecords;
import com.miajon.jobsearch.records.PredictionRecords;
import com.miajon.jobsearch.service.ExpenseService;
import com.miajon.jobsearch.tools.PredictionTools;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class ExpensesRestController {

    private final ExpenseService expenseService;

    public ExpensesRestController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/api/expenses/overview")
    public ExpenseRecords.ExpenseOverview expenses()
            throws InterruptedException, JsonProcessingException, PredictionException {
        Double totalAmount = expenseService.getTotalAmount();
        Double totalMonthlyAmount = expenseService.getTotalMonthlyAmount();

        List<ExpenseRecords.ExpensesByMonth> expensesPerMonthList = expenseService.getIncomePerMonth();

        PredictionRecords.NumericPrediction prediction = null;
        try {
            prediction = PredictionTools.predictNextMonthExpense(expensesPerMonthList);
        } catch (PredictionException e) {
            prediction = new PredictionRecords.NumericPrediction(0.0, 0.0);
        }

        return new ExpenseRecords.ExpenseOverview(totalAmount, totalMonthlyAmount, prediction.prediction());
    }

    @GetMapping("/api/expenses/latest")
    public Iterable<Expense> latestExpenses() {
        return expenseService.findLastTenExpenses();
    }

    @GetMapping("/api/expenses/monthly")
    public Iterable<Expense> monthlyExpenses() {
        return expenseService.findMonthlyExpenses();
    }

    @GetMapping("/api/expenses/per-month")
    public List<ExpenseRecords.ExpensesByMonth> expensesPerMonth() {
        List<ExpenseRecords.ExpensesByMonth> expensesPerMonth = expenseService.getExpensesPerMonth();
        Collections.reverse(expensesPerMonth);

        return expensesPerMonth.stream().map(expense -> expense.mapToAbs()).toList();
    }

    @PostMapping("/api/expenses")
    public void addExpense(@ModelAttribute @Valid ExpenseForm expense) {
        Double realAmount = expense.isIncome() ? expense.getAmount() : -expense.getAmount();
        Expense expenseModel = new Expense(expense.getConcept(), realAmount, expense.getMonthly());
        expenseService.saveExpense(expenseModel);
    }

    @DeleteMapping(value = "/api/expenses/{id}")
    public void deleteExpense(@PathVariable String id) {
        expenseService.deleteExpense(id);

    }

}
