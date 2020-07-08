package com.trade_analysis.service;

import com.trade_analysis.dao.StockMarketDao;
import com.trade_analysis.dtos.StockQueryDto;
import com.trade_analysis.exception.InvalidApiCallException;
import com.trade_analysis.exception.TooManyApiCallsException;
import com.trade_analysis.model.StockPrices;
import com.trade_analysis.model.StockPricesPeriod;
import com.trade_analysis.model.StockSymbol;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service("stockMarketService")
public class StockMarketService {
    private final StockMarketDao stockMarketDao;

    public StockMarketService(StockMarketDao stockMarketDao) {
        this.stockMarketDao = stockMarketDao;
    }

    public StockPrices getStockPricesFromStockQueryDto(StockQueryDto stockQueryDto) throws InvalidApiCallException, TooManyApiCallsException {
        return new StockPrices(getRawFromStockQueryDto(stockQueryDto));
    }

    public JSONObject getRawFromStockQueryDto(StockQueryDto stockQueryDto) {
        StockPricesPeriod period = StockPricesPeriod.valueOf(stockQueryDto.getPeriod());
        String apiKey = stockQueryDto.getApiKey();
        StockSymbol symbol = StockSymbol.valueOf(stockQueryDto.getSymbol());

        return switch(period) {
            case DAILY -> getDailyStockPricesFromLast100Days(apiKey, symbol);
            case HOUR_PERIOD -> getHourPeriodStockPrices(apiKey, symbol);
            case FIVE_MINUTES_PERIOD -> get5minPeriodStockPrices(apiKey, symbol);
        };
    }

    public JSONObject get5minPeriodStockPrices(String apiKey, StockSymbol symbol) {
        return stockMarketDao.get5minPeriodStockPrices(apiKey, symbol);
    }

    public JSONObject getHourPeriodStockPrices(String apiKey, StockSymbol symbol) {
        return stockMarketDao.getHourPeriodStockPrices(apiKey, symbol);
    }

    public JSONObject getDailyStockPricesFromLast100Days(String apiKey, StockSymbol symbol) {
        return stockMarketDao.getDailyStockPricesFromLast100Days(apiKey, symbol);
    }
}