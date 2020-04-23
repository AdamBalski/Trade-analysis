package com.trade_analysis.dtos_validation;

public enum UserSignUpValidationResult implements ValidationResult {
    SUCCESS("SUCCESS"),
    USERNAME_NOT_CORRECT("USERNAME_NOT_CORRECT"),
    PASSWORD_NOT_CORRECT("PASSWORD_NOT_CORRECT"),
    PASSWORDS_DIFFERENT("PASSWORDS_DIFFERENT"),
    // OTHER ERRORS
    ;

    String stringRepresentation;

    UserSignUpValidationResult(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override public boolean isSuccess() {
        return this == SUCCESS;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }
}
