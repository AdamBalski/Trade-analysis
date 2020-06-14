package com.trade_analysis.dtos_validation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockQueryValidationResultTest {
    @ParameterizedTest
    @CsvSource(value = {
            "SUCCESS, true",
            "SYMBOL_NOT_CORRECT, false"
    })
    void testIsSuccess(StockQueryValidationResult stockQueryValidationResult, boolean isSuccess) {
        assertEquals(isSuccess, stockQueryValidationResult.isSuccess());
    }
}
