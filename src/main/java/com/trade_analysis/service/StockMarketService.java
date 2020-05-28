package com.trade_analysis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trade_analysis.dao.StockMarketDao;
import com.trade_analysis.model.StockSymbol;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service("stockMarketService")
public class StockMarketService {
    @Autowired
    StockMarketDao stockMarketDao;

    public JSONObject get5minPeriodStockPrices(String apiKey, StockSymbol symbol) {
        return stockMarketDao.get5minPeriodStockPrices(apiKey, symbol);
    }

    public JSONObject getHourPeriodStockPrices(String apiKey, StockSymbol symbol) {
        return stockMarketDao.getHourPeriodStockPrices(apiKey, symbol);
    }

    public JSONObject getDailyStockPricesFromLast100Days(String apiKey, StockSymbol symbol) throws JsonProcessingException {
        return stockMarketDao.getDailyStockPricesFromLast100Days(apiKey, symbol);
    }

    public Map<Date, Object> addDerivatives(JSONObject stockPrices) throws JsonProcessingException {
        // TODO
        return null;
    }
}
