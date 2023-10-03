package com.miajon.jobsearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExpensesController {

    @GetMapping("/expenses")
    public String expenses() {
        return "expenses/expenses";
    }
}
