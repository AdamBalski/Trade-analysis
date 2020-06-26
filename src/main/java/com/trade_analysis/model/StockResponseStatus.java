package com.trade_analysis.model;

import com.trade_analysis.exception.InvalidApiCallException;
import com.trade_analysis.exception.TooManyApiCallsException;
import org.json.JSONObject;

public enum StockResponseStatus {
    TOO_MANY_API_CALLS("TOO_MANY_API_CALLS"),
    INVALID_API_CALL("INVALID_API_CALL"),
    SUCCESS("SUCCESS");

    String stringRepresentation;

    StockResponseStatus(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    public static StockResponseStatus valueOf(JSONObject raw) {
        if(raw.has("Error Message")) {
            return INVALID_API_CALL;
        }
        else if(raw.has("Note")) {
            return TOO_MANY_API_CALLS;
        }

        return SUCCESS;
    }

    public void throwException() throws TooManyApiCallsException, InvalidApiCallException {
        if(this == TOO_MANY_API_CALLS)  throw new TooManyApiCallsException();
        if(this == INVALID_API_CALL)    throw new InvalidApiCallException();
    }
}
