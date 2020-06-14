package com.trade_analysis.model;

import lombok.Getter;
import org.json.JSONObject;

import static com.trade_analysis.model.StockPricesPeriod.*;

@Getter
public class StockPrices {
    private final StockSymbol symbol;
    private final StockPricesPeriod period;
    private final JSONObject raw;
    private final JSONObject metaData;
    private final JSONObject timeSeries;

    public StockPrices(JSONObject raw) {
        this.raw = raw;
        period = StockPrices.getPeriod(raw);
        metaData = StockPrices.getMetaData(raw);
        symbol = StockPrices.getSymbol(raw);
        timeSeries = StockPrices.getTimeSeries(raw);
    }

    public StockPrices(JSONObject raw, StockSymbol symbol, StockPricesPeriod period) {
        this.raw = raw;
        this.symbol = symbol;
        this.period = period;
        metaData = StockPrices.getMetaData(raw);
        timeSeries = StockPrices.getTimeSeries(raw);
    }

    private static JSONObject getTimeSeries(JSONObject raw) throws IllegalStateException {
        for (String key : raw.keySet()) {
            if(key.startsWith("Time Series")) {
                return raw.getJSONObject(key);
            }
        }

        throw new IllegalStateException(
                        "There is no time series in JSON represantation of stock prices: " +
                        raw.toString());
    }

    private static StockSymbol getSymbol(JSONObject raw) {
        var metaData = StockPrices.getMetaData(raw);
        return StockSymbol.valueOf(metaData.getString("2. Symbol"));
    }

    private static StockPricesPeriod getPeriod(JSONObject raw) {
        String timeSeriesKey = "";
        for(String key : raw.keySet()) {
            if(key.startsWith("Time Series (")) {
                timeSeriesKey = key;
            }
        }

        String period = timeSeriesKey.substring(13, timeSeriesKey.length() - 1);
        return switch(period) {
            case "Daily" -> DAILY;
            case "60min" -> HOUR_PERIOD;
            case "5min" -> FIVE_MINUTES_PERIOD;
            default -> throw new IllegalStateException("Unexpected value: " + period);
        };
    }

    private static JSONObject getMetaData(JSONObject raw) {
        return raw.getJSONObject("Meta Data");
    }
}