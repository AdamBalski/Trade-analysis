package com.trade_analysis.model;

import com.trade_analysis.util.File;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.trade_analysis.model.StockPricesPeriod.*;
import static com.trade_analysis.model.StockSymbol.GOOGL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StockPricesTest {
    JSONObject google1Day;
    JSONObject expectedMetaData;
    JSONObject expectedTimeSeries;

    @BeforeEach
    void init() throws IOException {
        google1Day =
                new File("src/test/resources/examples of alpha vantage results/googl-1day")
                .getFileAsJSONObject();
        expectedMetaData =
                new File("src/test/resources/examples of alpha vantage results/googl-1day-metadata")
                .getFileAsJSONObject();
        expectedTimeSeries =
                new File("src/test/resources/examples of alpha vantage results/googl-1day-timeseries")
                .getFileAsJSONObject();
    }

    @Test
    void testOnlyJsonConstructor() {
        StockPrices stockPrices = new StockPrices(google1Day);

        assertEquals(google1Day, stockPrices.getRaw());
        assertEquals(GOOGL, stockPrices.getSymbol());
        assertEquals(DAILY, stockPrices.getPeriod());
        assertTrue(expectedMetaData.similar(stockPrices.getMetaData()));
        assertTrue(expectedTimeSeries.similar(stockPrices.getTimeSeries()));
    }

    @Test
    void testJsonSymbolAndPeriodConstructor() {
        StockPrices stockPrices = new StockPrices(google1Day, GOOGL, DAILY);

        assertEquals(google1Day, stockPrices.getRaw());
        assertEquals(GOOGL, stockPrices.getSymbol());
        assertEquals(DAILY, stockPrices.getPeriod());
        assertTrue(expectedMetaData.similar(stockPrices.getMetaData()));
        assertTrue(expectedTimeSeries.similar(stockPrices.getTimeSeries()));
    }

    @Test
    void testConstructorWith5minPeriod() {
        var json =  "{'Time Series (5min)': {}, " +
                "'Meta Data': {'2. Symbol': 'GOOGL'}}";

        var jsonObject = new JSONObject(json);
        StockPrices stockPrices = new StockPrices(jsonObject);

        assertEquals(FIVE_MINUTES_PERIOD, stockPrices.getPeriod());
    }

    @Test
    void testConstructorWithHourPeriod() {
        var json =  "{'Time Series (60min)': {}, " +
                "'Meta Data': {'2. Symbol': 'GOOGL'}}";

        var jsonObject = new JSONObject(json);
        StockPrices stockPrices = new StockPrices(jsonObject);

        assertEquals(HOUR_PERIOD, stockPrices.getPeriod());
    }

    @Test
    void testConstructorWithDailyPeriod() {
        var json =  "{'Time Series (Daily)': {}, " +
                    "'Meta Data': {'2. Symbol': 'GOOGL'}}";

        var jsonObject = new JSONObject(json);
        StockPrices stockPrices = new StockPrices(jsonObject);

        assertEquals(DAILY, stockPrices.getPeriod());
    }
}