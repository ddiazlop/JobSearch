package com.miajon.jobsearch.model.forms;

import com.miajon.jobsearch.model.validators.AmountNotZeroConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ExpenseForm {

    @NotBlank
    private String concept;

    @AmountNotZeroConstraint
    private Integer amount;

    public ExpenseForm() {
        this.concept = "";
        this.amount = 0;
    }
}
