package com.trade_analysis.dtos_validation;

import org.junit.jupiter.api.Test;

import static com.trade_analysis.dtos_validation.SimpleValidationResult.FAILURE;
import static com.trade_analysis.dtos_validation.SimpleValidationResult.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidatorTest {
    public static final Validator<Object, SimpleValidationResult> returnsSuccess = o -> SUCCESS;
    public static final Validator<Object, SimpleValidationResult> returnsFailure = o -> FAILURE;

    @Test
    void testReturnsSuccessAndReturnsFailure() {
        Validator<Object, SimpleValidationResult> validator = returnsSuccess.and(returnsFailure);
        SimpleValidationResult simpleValidationResult = validator.validate(new Object());

        assertEquals(FAILURE, simpleValidationResult);
    }

    @Test
    void testReturnsSuccessAndReturnsSuccess() {
        Validator<Object, SimpleValidationResult> validator = returnsSuccess.and(returnsSuccess);
        SimpleValidationResult simpleValidationResult = validator.validate(new Object());

        assertEquals(SUCCESS, simpleValidationResult);
    }

    @Test
    void testReturnsFailureAndReturnsFailure() {
        Validator<Object, SimpleValidationResult> validator = returnsFailure.and(returnsFailure);
        SimpleValidationResult simpleValidationResult = validator.validate(new Object());

        assertEquals(FAILURE, simpleValidationResult);
    }

    @Test
    void testReturnFailureAndReturnsSuccess() {
        Validator<Object, SimpleValidationResult> validator = returnsFailure.and(returnsSuccess);
        SimpleValidationResult simpleValidationResult = validator.validate(new Object());

        assertEquals(FAILURE, simpleValidationResult);
    }

}

enum SimpleValidationResult implements ValidationResult {
    SUCCESS,
    FAILURE;


    @Override
    public boolean isSuccess() {
        return this == SUCCESS;
    }
}