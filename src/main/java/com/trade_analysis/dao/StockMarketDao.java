package com.trade_analysis.dao;

import com.trade_analysis.model.StockSymbol;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository("stockMarketDao")
public class StockMarketDao {
    public StockMarketDao() {
    }

    public JSONObject get5minPeriodStockPrices(String apiKey, StockSymbol symbol) {
        var url = String.format("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=%s&interval=5min&outputsize=full&apikey=%s", symbol.name(), apiKey);
        var restTemplate = new RestTemplate();
        var str = restTemplate.getForObject(url, String.class);


        return new JSONObject(str);
    }

    public JSONObject getHourPeriodStockPrices(String apiKey, StockSymbol symbol) {
        var url = String.format("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=%s&interval=60min&outputsize=full&apikey=%s", symbol.name(), apiKey);
        var restTemplate = new RestTemplate();
        var str = restTemplate.getForObject(url, String.class);


        return new JSONObject(str);
    }

    public JSONObject getDailyStockPricesFromLast100Days(String apiKey, StockSymbol symbol) {
        var url = String.format("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=%s&outputsize=compact&apikey=%s", symbol.name(), apiKey);
        var restTemplate = new RestTemplate();
        var str = restTemplate.getForObject(url, String.class);


        return new JSONObject(str);
    }
}