package com.gongdb.admin.global.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.gongdb.admin.announcement.dto.request.LanguageScoreInputDto;

public class UniqueLanguageScoreInputValidator
    implements ConstraintValidator<UniqueLanguageScoreInput, List<LanguageScoreInputDto>> {

    @Override
    public boolean isValid(List<LanguageScoreInputDto> values, ConstraintValidatorContext context) {
        return values.size() == values.stream().distinct().count();
    }
}
