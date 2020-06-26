package com.trade_analysis.model;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static com.trade_analysis.model.StockResponseStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StockResponseStatusTest {
    @Test
    void testValueOfWithSuccess() {
        JSONObject raw = new JSONObject(
                "{" +
                        "   'Meta Data': {}," +
                        "   'Time Series (Daily)': {}" +
                        "}"
        );

        StockResponseStatus actual = StockResponseStatus.valueOf(raw);
        assertEquals(SUCCESS, actual);
    }

    @Test
    void testValueOfWithTooManyApiCalls() {
        JSONObject raw = new JSONObject(
                "{" +
                        "   'Note':" +
                        "       'Thank you for using Alpha Vantage! " +
                        "       Our standard API call frequency is 5 calls per minute and 500 calls per day. " +
                        "       Please visit https://www.alphavantage.co/premium/ " +
                        "       if you would like to target a higher API call frequency.'" +
                        "}"
        );

        StockResponseStatus actual = StockResponseStatus.valueOf(raw);
        assertEquals(TOO_MANY_API_CALLS, actual);
    }

    @Test
    void testValueOfWithInvalidApiCall() {
        JSONObject raw = new JSONObject(
                "{" +
                        "   'Error Message': " +
                        "       'Invalid API call. Please retry or visit the documentation (https://www.alphavantage.co/documentation/) for TIME_SERIES_INTRADAY.'" +
                        "}"
        );

        StockResponseStatus actual = StockResponseStatus.valueOf(raw);
        assertEquals(INVALID_API_CALL, actual);
    }
}