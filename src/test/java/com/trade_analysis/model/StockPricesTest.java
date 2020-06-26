package com.trade_analysis.model;

import com.trade_analysis.exception.InvalidApiCallException;
import com.trade_analysis.exception.TooManyApiCallsException;
import com.trade_analysis.util.File;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;

import static com.trade_analysis.model.StockPricesPeriod.*;
import static com.trade_analysis.model.StockSymbol.AMZN;
import static org.junit.jupiter.api.Assertions.*;

public class StockPricesTest {
    private JSONObject AMZNe1Day;
    private JSONObject expectedMetaData;
    private JSONObject expectedTimeSeries;

    private JSONObject simple;
    private JSONObject simpleFinalState;

    @BeforeEach
    void init() throws IOException {
        AMZNe1Day =
                new File("src/test/resources/examples of alpha vantage results/amzn-1day")
                .getFileAsJSONObject();
        expectedMetaData =
                new File("src/test/resources/examples of alpha vantage results/amzn-1day-metadata")
                .getFileAsJSONObject();
        expectedTimeSeries =
                new File("src/test/resources/examples of alpha vantage results/amzn-1day-timeseries")
                .getFileAsJSONObject();

        simple =
                new File("src/test/resources/examples of alpha vantage results/simple")
                        .getFileAsJSONObject();
        simpleFinalState =
                new File("src/test/resources/examples of alpha vantage results/simple-final-json")
                        .getFileAsJSONObject();
    }

    @Test
    void testOnlyJsonConstructor() throws InvalidApiCallException, TooManyApiCallsException {
        StockPrices stockPrices = new StockPrices(AMZNe1Day);

        assertEquals(AMZNe1Day, stockPrices.getRaw());
        assertEquals(AMZN, stockPrices.getSymbol());
        assertEquals(DAILY, stockPrices.getPeriod());
        assertEquals("Time Series (Daily)", stockPrices.getTimeSeriesLabel());
        assertTrue(expectedMetaData.similar(stockPrices.getMetaData()));
        assertTrue(expectedTimeSeries.similar(stockPrices.getTimeSeries()));
    }

    @Test
    void testOnlyJsonConstructorThatShouldThrowInvalidApiCallException() {
        JSONObject raw = new JSONObject("{'Error Message': ''}");

        Executable executable = () -> new StockPrices(raw);
        assertThrows(InvalidApiCallException.class, executable);
    }

    @Test
    void testOnlyJsonConstructorThatShouldThrowTooManyApiCallsException() {
        JSONObject raw = new JSONObject("{'Note': ''}");

        Executable executable = () -> new StockPrices(raw);
        assertThrows(TooManyApiCallsException.class, executable);
    }

    @Test
    void testJsonSymbolAndPeriodConstructor() throws InvalidApiCallException, TooManyApiCallsException {
        StockPrices stockPrices = new StockPrices(AMZNe1Day, AMZN, DAILY);

        assertEquals(AMZNe1Day, stockPrices.getRaw());
        assertEquals(AMZN, stockPrices.getSymbol());
        assertEquals(DAILY, stockPrices.getPeriod());
        assertEquals("Time Series (Daily)", stockPrices.getTimeSeriesLabel());
        assertTrue(expectedMetaData.similar(stockPrices.getMetaData()));
        assertTrue(expectedTimeSeries.similar(stockPrices.getTimeSeries()));
    }

    @Test
    void testJsonSymbolAndPeriodConstructorThatShouldThrowInvalidApiCallException() {
        JSONObject raw = new JSONObject("{'Error Message': ''}");

        Executable executable = () -> new StockPrices(raw, AMZN, DAILY);
        assertThrows(InvalidApiCallException.class, executable);
    }

    @Test
    void testJsonSymbolAndPeriodConstructorThatShouldThrowTooManyApiCallslException() {
        JSONObject raw = new JSONObject("{'Note': ''}");

        Executable executable = () -> new StockPrices(raw, AMZN, DAILY);
        assertThrows(TooManyApiCallsException.class, executable);
    }

    @Test
    void testConstructorWith5minPeriod() throws InvalidApiCallException, TooManyApiCallsException {
        var json =  "{'Time Series (5min)': {}, " +
                "'Meta Data': {'2. Symbol': 'AMZN'}}";

        var jsonObject = new JSONObject(json);
        StockPrices stockPrices = new StockPrices(jsonObject);

        assertEquals("Time Series (5min)", stockPrices.getTimeSeriesLabel());
        assertEquals(FIVE_MINUTES_PERIOD, stockPrices.getPeriod());
    }

    @Test
    void testConstructorWithHourPeriod() throws InvalidApiCallException, TooManyApiCallsException {
        var json =  "{'Time Series (60min)': {}, " +
                "'Meta Data': {'2. Symbol': 'AMZN'}}";

        var jsonObject = new JSONObject(json);
        StockPrices stockPrices = new StockPrices(jsonObject);

        assertEquals("Time Series (60min)", stockPrices.getTimeSeriesLabel());
        assertEquals(HOUR_PERIOD, stockPrices.getPeriod());
    }

    @Test
    void testConstructorWithDailyPeriod() throws InvalidApiCallException, TooManyApiCallsException {
        var json =  "{'Time Series (Daily)': {}, " +
                    "'Meta Data': {'2. Symbol': 'AMZN'}}";

        var jsonObject = new JSONObject(json);
        StockPrices stockPrices = new StockPrices(jsonObject);

        assertEquals("Time Series (Daily)", stockPrices.getTimeSeriesLabel());
        assertEquals(DAILY, stockPrices.getPeriod());
    }

    @Test
    void testFinalJSON() throws InvalidApiCallException, TooManyApiCallsException {
        StockPrices stockPrices = new StockPrices(simple);

        assertTrue(simpleFinalState.similar(stockPrices.getFinalJSON()));
    }

    @Test
    void testFinalJSONWithOneTimeSeriesChildren() throws InvalidApiCallException, TooManyApiCallsException {
        JSONObject raw = new JSONObject(
                "{" +
                        "    'Meta Data': {" +
                        "        '2. Symbol': 'AMZN'" +
                        "    }," +
                        "    'Time Series (60min)': {" +
                        "        '2020-06-12 15:30:00': {" +
                        "            '1. open': '0.0'," +
                        "            '4. close': '0.0'" +
                        "         }" +
                        "    }" +
                        "}"
        );
        StockPrices stockPrices = new StockPrices(raw);

        JSONObject expected = new JSONObject(
                "{" +
                        "    'Meta Data': {" +
                        "        '2. Symbol': 'AMZN'" +
                        "    }," +
                        "    'Time Series (60min)': {" +
                        "        '2020-06-12 15:30:00': {" +
                        "            '1. open': '0.0'," +
                        "            '4. close': '0.0'," +
                        "            'derivative': 0.0" +
                        "         }" +
                        "    }" +
                        "}"
        );

        System.out.println(expected);
        System.out.println(raw);

        assertTrue(expected.similar(stockPrices.getFinalJSON()));
    }

    @Test
    void testFinalJSONWithEmptyTimeSeries() throws InvalidApiCallException, TooManyApiCallsException {
        JSONObject raw = new JSONObject(
                        "{" +
                                "    'Meta Data': {" +
                                "        '2. Symbol': 'AMZN'" +
                                "    }," +
                                "    'Time Series (5min)': {" +
                                "    }" +
                                "}"
        );
        StockPrices stockPrices = new StockPrices(raw);

        assertTrue(raw.similar(stockPrices.getFinalJSON()));
    }
}