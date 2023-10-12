package com.miajon.jobsearch.model.validators;

import com.miajon.jobsearch.model.Expense;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AmountNotZeroValidator.class)
public @interface AmountNotZeroConstraint {
    String message() default "Amount must not be zero";
    Class<?>[] groups() default {};
    Class<? extends Expense>[] payload() default {};
}
