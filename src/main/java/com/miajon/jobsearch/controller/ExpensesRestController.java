package com.miajon.jobsearch.controller;

import com.miajon.jobsearch.model.Expense;
import com.miajon.jobsearch.model.forms.ExpenseForm;
import com.miajon.jobsearch.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ExpensesRestController {

    private final ExpenseService expenseService;

    public ExpensesRestController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public record ExpenseOverview(Double total, Double monthly) {
    }


    @GetMapping("/api/expenses/overview")
    public ExpenseOverview expenses() {
        Double totalAmount = expenseService.getTotalAmount();
        Double totalMonthlyAmount = expenseService.getTotalMonthlyAmount();
        return new ExpenseOverview(totalAmount, totalMonthlyAmount);
    }

    @GetMapping("/api/expenses/latest")
    public Iterable<Expense> latestExpenses() {
        return expenseService.findAllByOrderByDateDesc();
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
