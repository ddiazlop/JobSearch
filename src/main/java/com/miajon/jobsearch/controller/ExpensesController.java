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

    private static final String MODEL_FORM = "expenseForm";
    private static final String MODEL_EXPENSES_LIST = "expenses";

    private static final String DEFAULT_VIEW = "expenses/expenses";
    private final ExpenseService expenseService;
    private Boolean isMonthlyList = false;

    public ExpensesController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    private void refreshView(Model model) {
        Double totalAmount = expenseService.getTotalAmount();
        Double totalMonthlyAmount = expenseService.getTotalMonthlyAmount();
        model.addAttribute(MODEL_FORM, new ExpenseForm());
        model.addAttribute("totalAmount", totalAmount == null ? 0 : totalAmount);
        model.addAttribute("totalMonthlyAmount", totalMonthlyAmount == null ? 0 : totalMonthlyAmount);
        model.addAttribute(MODEL_EXPENSES_LIST, (this.isMonthlyList) ? expenseService.findMonthlyExpenses() : expenseService.findAllByOrderByDateDesc());
        model.addAttribute("isMonthlyList", this.isMonthlyList);

    }


    @GetMapping("/expenses")
    public String expenses(Model model) {
        this.isMonthlyList = false;
        this.refreshView(model);
        return DEFAULT_VIEW;
    }

    @GetMapping("/expenses/monthly")
    public String monthlyExpenses(Model model) {
        this.isMonthlyList = true;
        this.refreshView(model);
        return DEFAULT_VIEW;
    }

    @PostMapping("/expenses")
    public String addExpense(@ModelAttribute @Valid ExpenseForm expenseForm, BindingResult result, Model model) {

        if (!result.hasErrors()) {
            Double realAmount = expenseForm.isIncome() ? expenseForm.getAmount() : -expenseForm.getAmount();
            Expense expense = new Expense(expenseForm.getConcept(), realAmount, expenseForm.getMonthly());
            expenseService.saveExpense(expense);
            return redirectToOverview();

        }
        model.addAttribute(MODEL_EXPENSES_LIST, expenseService.findAllExpenses());
        model.addAttribute(MODEL_FORM, expenseForm);
        return DEFAULT_VIEW;


    }

    @PostMapping("/expenses/delete")
    public String deleteExpense(@RequestParam("itemId") String itemId, Model model) {
        expenseService.deleteExpense(itemId);
        this.refreshView(model);
        return redirectToOverview();
    }


    private String redirectToOverview() {
        return "redirect:/" + (this.isMonthlyList ? "expenses/monthly" : MODEL_EXPENSES_LIST);
    }


}
