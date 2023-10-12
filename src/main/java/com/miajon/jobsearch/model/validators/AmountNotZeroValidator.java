package com.miajon.jobsearch.model.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AmountNotZeroValidator implements ConstraintValidator<AmountNotZeroConstraint, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != 0;
    }
}
