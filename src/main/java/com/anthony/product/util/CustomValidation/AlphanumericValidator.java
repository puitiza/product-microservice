package com.anthony.product.util.CustomValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AlphanumericValidator implements ConstraintValidator<Alphanumeric, String> {

    private static final Pattern pattern = Pattern.compile("^[A-Za-z\\d?@._-]+$");

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return pattern.matcher(s).find();
    }
}
