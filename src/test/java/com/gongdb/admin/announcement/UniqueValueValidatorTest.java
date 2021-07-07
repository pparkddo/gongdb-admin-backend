package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.gongdb.admin.global.validation.UniqueValue;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class UniqueValueValidatorTest {

    @Test
    public void validValuesTest() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        SampleDto values = SampleDto.of(List.of("one", "two", "three"));
        Set<ConstraintViolation<SampleDto>> constraintViolations = validator.validate(values);

        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void invalidValuesTest() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        SampleDto values = SampleDto.of(List.of("one", "two", "one"));
        Set<ConstraintViolation<SampleDto>> constraintViolations = validator.validate(values);

        assertTrue(!constraintViolations.isEmpty());
    }

    private static class SampleDto {

        @UniqueValue
        private List<String> values;

        private SampleDto(List<String> values) {
            this.values = values;
        }

        public static SampleDto of(List<String> values) {
            return new SampleDto(values);
        }
    }
}
