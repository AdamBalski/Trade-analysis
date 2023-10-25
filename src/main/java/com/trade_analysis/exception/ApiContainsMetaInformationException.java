package com.trade_analysis.exception;

public class ApiContainsMetaInformationException extends Exception {
    public ApiContainsMetaInformationException() {
        this("CANT_PARSE_API_EXCEPTION");
    }
    public ApiContainsMetaInformationException(String message) {
        super(message);
    }
}
