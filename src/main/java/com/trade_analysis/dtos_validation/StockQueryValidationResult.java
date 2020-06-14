package com.trade_analysis.dtos_validation;

public enum StockQueryValidationResult implements ValidationResult {
    SUCCESS("SUCCESS"),
    SYMBOL_NOT_CORRECT("SYMBOL_NOT_CORRECT"),
    PERIOD_NOT_CORRECT("PERIOD_NOT_CORRECT");

    String stringRepresentation;

    StockQueryValidationResult(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public boolean isSuccess() {
        return this == SUCCESS;
    }
}
