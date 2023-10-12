package com.miajon.jobsearch.model.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AmountNotZeroValidator implements ConstraintValidator<AmountNotZeroConstraint, Double> {
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value != 0.;
    }
}
