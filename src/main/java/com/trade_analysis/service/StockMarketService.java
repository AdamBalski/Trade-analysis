package com.trade_analysis.service;

import com.trade_analysis.dao.StockMarketDao;
import com.trade_analysis.dtos.StockQueryDto;
import com.trade_analysis.exception.ApiContainsMetaInformationException;
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

    public StockPrices getStockPricesFromStockQueryDto(StockQueryDto stockQueryDto) throws InvalidApiCallException, TooManyApiCallsException, ApiContainsMetaInformationException {
        return new StockPrices(getRawFromStockQueryDto(stockQueryDto));
    }

    public JSONObject getRawFromStockQueryDto(StockQueryDto stockQueryDto) throws ApiContainsMetaInformationException {
        StockPricesPeriod period = StockPricesPeriod.valueOf(stockQueryDto.getPeriod());
        String apiKey = stockQueryDto.getApiKey();
        StockSymbol symbol = StockSymbol.valueOf(stockQueryDto.getSymbol());

        JSONObject raw =  switch(period) {
            case DAILY -> getDailyStockPricesFromLast100Days(apiKey, symbol);
            case HOUR_PERIOD -> getHourPeriodStockPrices(apiKey, symbol);
            case FIVE_MINUTES_PERIOD -> get5minPeriodStockPrices(apiKey, symbol);
        };
        // throws ApiContainsMetaInformationException if result contains "Information" key
        // e.g.: "{"Information": "your api key is not a premium key, but this endpoint is reachable only for premium users"}"
        return filterIfContainsMetaInformation(raw);
    }

    private JSONObject get5minPeriodStockPrices(String apiKey, StockSymbol symbol) {
        return stockMarketDao.get5minPeriodStockPrices(apiKey, symbol);
    }

    private JSONObject getHourPeriodStockPrices(String apiKey, StockSymbol symbol) {
        return stockMarketDao.getHourPeriodStockPrices(apiKey, symbol);
    }

    private JSONObject getDailyStockPricesFromLast100Days(String apiKey, StockSymbol symbol) {
        return stockMarketDao.getDailyStockPricesFromLast100Days(apiKey, symbol);
    }

    private JSONObject filterIfContainsMetaInformation(JSONObject response) throws ApiContainsMetaInformationException {
        if(response.has("Information")) {
            Object informationValue = response.get("Information");
            if(informationValue instanceof String) {
                throw new ApiContainsMetaInformationException((String) informationValue);
            }
            else throw new ApiContainsMetaInformationException();
        }
        return response;
    }
}