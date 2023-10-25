package com.miajon.jobsearch.model.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ExpenseForm {

    @NotBlank
    private String concept;

    @Positive
    private Double amount;

    @NotNull
    private Boolean income;

    @NotNull
    private Boolean monthly;

    public Boolean isIncome() {
        return this.income;
    }

    public ExpenseForm() {
        this.concept = "";
        this.amount = 0.;
        this.income = false;
        this.monthly = false;
    }
}
