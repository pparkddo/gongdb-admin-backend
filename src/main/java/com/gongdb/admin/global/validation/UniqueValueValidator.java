package com.gongdb.admin.global.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, List<String>> {

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        return values.size() == values.stream().distinct().count();
    }
}
