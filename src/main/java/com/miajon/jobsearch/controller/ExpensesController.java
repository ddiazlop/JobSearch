package com.miajon.jobsearch.controller;

import com.miajon.jobsearch.model.Expense;
import com.miajon.jobsearch.model.forms.ExpenseForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ExpensesController {

    private static final String MODEL_FORM = "expenseForm";
    private static final String DEFAULT_VIEW = "expenses/expenses";

    @GetMapping("/expenses")
    public String expenses(Model model) {
        ExpenseForm expenseForm = new ExpenseForm();
        model.addAttribute(MODEL_FORM, expenseForm);
        return DEFAULT_VIEW;
    }

    @PostMapping("/expenses")
    public String addExpense(@ModelAttribute @Valid ExpenseForm expenseForm,BindingResult result, Model model) {
        if (!result.hasErrors()) {
            Expense expense = new Expense(expenseForm.getConcept(), expenseForm.getAmount());
            model.addAttribute(MODEL_FORM, expense);
            return DEFAULT_VIEW;
        }
        model.addAttribute(MODEL_FORM, expenseForm);
        return DEFAULT_VIEW;
    }
}
