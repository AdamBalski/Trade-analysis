package com.trade_analysis.model;

import com.trade_analysis.util.DateUtil;
import lombok.Getter;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.trade_analysis.model.StockPricesPeriod.*;
import static com.trade_analysis.util.DateUtil.DATE_FORMATTER;
import static java.time.Duration.between;

@Getter
public class StockPrices {
    private final StockSymbol symbol;
    private final StockPricesPeriod period;
    private final JSONObject raw;
    private final JSONObject finalJSON;
    private final JSONObject metaData;
    private final JSONObject timeSeries;

    public StockPrices(JSONObject raw) {
        this.raw = raw;
        period = StockPrices.getPeriod(raw);
        metaData = StockPrices.getMetaData(raw);
        symbol = StockPrices.getSymbol(raw);
        timeSeries = StockPrices.getTimeSeries(raw);
        finalJSON = addDerivatives(this);
    }

    public StockPrices(JSONObject raw, StockSymbol symbol, StockPricesPeriod period) {
        this.raw = raw;
        this.symbol = symbol;
        this.period = period;
        metaData = StockPrices.getMetaData(raw);
        timeSeries = StockPrices.getTimeSeries(raw);
        finalJSON = addDerivatives(this);
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

    private static JSONObject addDerivatives(StockPrices stockPrices) { // todo add cases to test
        DateTimeFormatter formatter = getDateTimeFormatter(stockPrices);
        Duration duration = stockPrices.period.duration;

        Function<String, LocalDateTime> parseToDate = getParseStringToDate(stockPrices, formatter);
        Function<LocalDateTime, String> parseToString = getParseDateToString(formatter);

        JSONObject timeSeries = stockPrices.timeSeries;

        Map<String, Object> map = timeSeries.toMap();
        ArrayList<LocalDateTime> dates = map.keySet()
                .stream()
                .map(parseToDate)
                .sorted().collect(Collectors.toCollection(ArrayList::new));

        double lastDerivative = 0;

        for(int i = 0; i < dates.size() - 1; i++) {
            Duration difference = between(dates.get(i), dates.get(i + 1));
            int unitAmount = (int) difference.dividedBy(duration);

            String date1 = parseToString.apply(dates.get(i));
            String date2 = parseToString.apply(dates.get(i + 1));

            double moneyStart = timeSeries.getJSONObject(date1).getDouble("1. open");
            double moneyEnd = timeSeries.getJSONObject(date2).getDouble("1. open");

            double derivative = (moneyEnd - moneyStart) / unitAmount;
            lastDerivative = derivative;

            //noinspection unchecked
            ((HashMap<String, Object>) map.get(date1)).put("derivative", derivative);
        }

        if(map.size() > 0) {
            String lastDate = parseToString.apply(dates.get(dates.size() - 1));

            //noinspection unchecked
            ((HashMap<String, Object>) map.get(lastDate)).put("derivative", lastDerivative);
        }

        JSONObject result = new JSONObject();

        result.put("Meta Data", stockPrices.metaData);
        String timeSeriesLabel = getTimeSeriesLabel(stockPrices);
        result.put(timeSeriesLabel, map);

        return result;
    }

    private static String getTimeSeriesLabel(StockPrices stockPrices) {
        return switch(stockPrices.period) {
            case DAILY -> "Time Series (Daily)";
            case HOUR_PERIOD -> "Time Series (60min)";
            case FIVE_MINUTES_PERIOD -> "Time Series (5min)";
        };
    }

    private static DateTimeFormatter getDateTimeFormatter(StockPrices stockPrices) {
        return switch(stockPrices.period) {
                case DAILY -> DATE_FORMATTER;
                case HOUR_PERIOD, FIVE_MINUTES_PERIOD -> DateUtil.DATE_TIME_FORMATTER;
            };
    }

    private static Function<LocalDateTime, String> getParseDateToString(DateTimeFormatter formatter) {
        return date -> date.format(formatter);
    }

    private static Function<String, LocalDateTime> getParseStringToDate(StockPrices stockPrices, DateTimeFormatter formatter) {
        return string -> {
                if(stockPrices.period == DAILY) {
                    return LocalDate.parse(string, formatter).atStartOfDay();
                }
                return LocalDateTime.parse(string, formatter);
            };
    }
}