package com.miajon.jobsearch.controller;

import com.miajon.jobsearch.service.ExpenseService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


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

}
