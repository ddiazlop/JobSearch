package com.miajon.jobsearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JobScrapController {

    @GetMapping()
    public String home(Model model) {
        model.addAttribute("message", "Hello World 5");
        return "home";

    }

}
