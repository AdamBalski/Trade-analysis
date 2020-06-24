package com.trade_analysis.exception;

public class EmailVerificationTokenNotFoundException extends Exception{
    public EmailVerificationTokenNotFoundException() {
        this("EmailVerificationToken could not be found.");
    }

    public EmailVerificationTokenNotFoundException(String message) {
        super(message);
    }
}
