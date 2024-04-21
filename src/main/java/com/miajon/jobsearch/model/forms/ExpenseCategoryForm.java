package com.miajon.jobsearch.model.forms;

import jakarta.validation.constraints.NotBlank;

public class ExpenseCategoryForm {
    @NotBlank
    private String name;

    @NotBlank
    private String keywords;

    public ExpenseCategoryForm() {
        name = "";
        keywords = "";
    }
}
