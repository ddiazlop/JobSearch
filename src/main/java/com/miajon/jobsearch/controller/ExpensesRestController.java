package com.miajon.jobsearch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miajon.jobsearch.exception.PredictionException;
import com.miajon.jobsearch.model.Expense;
import com.miajon.jobsearch.model.forms.ExpenseForm;
import com.miajon.jobsearch.records.ExpenseRecords;
import com.miajon.jobsearch.records.PredictionRecords;
import com.miajon.jobsearch.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class ExpensesRestController {

    private final ExpenseService expenseService;

    public ExpensesRestController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/api/expenses/overview")
    public ExpenseRecords.ExpenseOverview expenses() throws InterruptedException, JsonProcessingException, PredictionException {
        Double totalAmount = expenseService.getTotalAmount();
        Double totalMonthlyAmount = expenseService.getTotalMonthlyAmount();

        List<ExpenseRecords.ExpensesByMonth> expensesPerMonthList = expenseService.getIncomePerMonth();

        PredictionRecords.NumericPrediction prediction = null;
        try {
            prediction = predictNextMonthExpense(expensesPerMonthList);
        } catch (PredictionException e) {
            prediction = new PredictionRecords.NumericPrediction(0.0, 0.0);
        }
         


        return new ExpenseRecords.ExpenseOverview(totalAmount, totalMonthlyAmount, prediction.prediction());
    }

    private PredictionRecords.NumericPrediction predictNextMonthExpense(List<ExpenseRecords.ExpensesByMonth> expensesPerMonthList) throws JsonProcessingException, PredictionException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        String expensesPerMonth = objectMapper.writeValueAsString(expensesPerMonthList);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/predict/expensesPerMonth/linear"))
                .header("Content-Type", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(expensesPerMonth))
                .build();

        HttpResponse<String> response;
        try {
            response = java.net.http.HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new PredictionException("Error while trying to connect to the prediction service");
        }

        if (response.statusCode() != 200) {
            throw new PredictionException("Error while trying to make the prediction");
        }

        ObjectMapper responseMapper = new ObjectMapper();
        return responseMapper.readValue(response.body(), PredictionRecords.NumericPrediction.class);
    }


    @GetMapping("/api/expenses/latest")
    public Iterable<Expense> latestExpenses() {
        return expenseService.findLastTenExpenses();
    }

    @GetMapping("/api/expenses/monthly")
    public Iterable<Expense> monthlyExpenses() {
        return expenseService.findMonthlyExpenses();
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
