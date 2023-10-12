package com.miajon.jobsearch.controller;

import com.miajon.jobsearch.model.Expense;
import com.miajon.jobsearch.model.forms.ExpenseForm;
import com.miajon.jobsearch.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ExpensesController {

    private final ExpenseService expenseService;

    public ExpensesController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    private static final String MODEL_FORM = "expenseForm";
    private static final String MODEL_EXPENSES_LIST = "expenses";
    private static final String DEFAULT_VIEW = "expenses/expenses";


    private void refreshView(Model model) {
        model.addAttribute(MODEL_FORM, new ExpenseForm());
        model.addAttribute(MODEL_EXPENSES_LIST, expenseService.findAllExpenses());
    }

    @GetMapping("/expenses")
    public String expenses(Model model) {
        this.refreshView(model);
        return DEFAULT_VIEW;
    }

    @PostMapping("/expenses")
    public String addExpense(@ModelAttribute @Valid ExpenseForm expenseForm,BindingResult result, Model model) {

        if (!result.hasErrors()) {
            Expense expense = new Expense(expenseForm.getConcept(), expenseForm.getAmount());
            expenseService.saveExpense(expense);
            this.refreshView(model);

        } else {
            model.addAttribute(MODEL_EXPENSES_LIST, expenseService.findAllExpenses());
            model.addAttribute(MODEL_FORM, expenseForm);
        }
        return DEFAULT_VIEW;
    }

    @PostMapping("/expenses/delete")
    public String deleteExpense(@RequestParam("itemId") String itemId, Model model) {
        expenseService.deleteExpense(itemId);
        this.refreshView(model);
        return DEFAULT_VIEW;
    }



}
