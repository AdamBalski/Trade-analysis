package com.trade_analysis.dtos_validation;

import com.trade_analysis.dtos.StockQueryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class StockQueryValidatorTest {
    @ParameterizedTest
    @CsvSource(value = {
            "'AMZN', 'DAILY', SUCCESS",
            "'NOT_SYMBOL', 'DAILY', SYMBOL_NOT_CORRECT",
            "'MSFT', 'NOT_PERIOD', PERIOD_NOT_CORRECT",
            "'NOT_SYMBOL', 'NOT_PERIOD', SYMBOL_NOT_CORRECT"
    })
    void testFullValidator(String symbol, String period, StockQueryValidationResult expected) {
        StockQueryDto stockQueryDto =
                StockQueryDto.builder()
                        .symbol(symbol)
                        .period(period)
                        .build();

        StockQueryValidationResult actual = StockQueryValidator.fullValidator.validate(stockQueryDto);

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "'AMZN', SUCCESS",
            "'NOT_SYMBOL', SYMBOL_NOT_CORRECT"
    })
    void testSymbolValidator(String symbol, StockQueryValidationResult expected) {
        StockQueryDto stockQueryDto =
                StockQueryDto.builder()
                        .symbol(symbol)
                        .build();

        StockQueryValidationResult actual = StockQueryValidator.symbolValidator.validate(stockQueryDto);

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "'DAILY', SUCCESS",
            "'NOT_PERIOD', PERIOD_NOT_CORRECT"
    })
    void testPeriodValidator(String period, StockQueryValidationResult expected) {
        StockQueryDto stockQueryDto =
                StockQueryDto.builder()
                        .period(period)
                        .build();

        StockQueryValidationResult actual = StockQueryValidator.periodValidator.validate(stockQueryDto);

        Assertions.assertEquals(expected, actual);
    }
}
