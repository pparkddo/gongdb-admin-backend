package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.gongdb.admin.announcement.dto.request.LanguageScoreInputDto;
import com.gongdb.admin.global.validation.UniqueLanguageScoreInput;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class UniqueLanguageScoreInputValidatorTest {

    @Test
    public void validLanguageScoreInputTest() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        List<LanguageScoreInputDto> languageScores = new ArrayList<>();
        languageScores.add(LanguageScoreInputDto.builder().name("one").score("first score").build());
        languageScores.add(LanguageScoreInputDto.builder().name("two").score("second score").build());
        languageScores.add(LanguageScoreInputDto.builder().name("three").score("third score").build());
        SampleDto values = SampleDto.of(languageScores);

        Set<ConstraintViolation<SampleDto>> constraintViolations = validator.validate(values);

        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void invalidLanguageScoreInputTest() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        List<LanguageScoreInputDto> languageScores = new ArrayList<>();
        languageScores.add(LanguageScoreInputDto.builder().name("one").score("first score").build());
        languageScores.add(LanguageScoreInputDto.builder().name("two").score("second score").build());
        languageScores.add(LanguageScoreInputDto.builder().name("one").score("first score").build());
        SampleDto values = SampleDto.of(languageScores);

        Set<ConstraintViolation<SampleDto>> constraintViolations = validator.validate(values);

        assertTrue(!constraintViolations.isEmpty());
    }

    private static class SampleDto {

        @UniqueLanguageScoreInput
        private List<LanguageScoreInputDto> languageScores;

        private SampleDto(List<LanguageScoreInputDto> languageScores) {
            this.languageScores = languageScores;
        }

        public static SampleDto of(List<LanguageScoreInputDto> values) {
            return new SampleDto(values);
        }
    }
}
