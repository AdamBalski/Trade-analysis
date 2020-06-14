package com.trade_analysis.service;

import com.trade_analysis.dao.StockMarketDao;
import com.trade_analysis.util.File;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static com.trade_analysis.model.StockSymbol.GOOGL;

public class StockMarketServiceTest {
    @InjectMocks
    StockMarketService stockMarketService;

    @Mock
    StockMarketDao stockMarketDao;

    private JSONObject daily;
    private JSONObject hourPeriod;
    private JSONObject fiveMinPeriod;

    @BeforeEach
    void init() throws IOException {
        MockitoAnnotations.initMocks(this);

        daily =
                new File("src/test/resources/examples of alpha vantage results/googl-1day")
                        .getFileAsJSONObject();
        hourPeriod =
                new File("src/test/resources/examples of alpha vantage results/googl-1hour")
                        .getFileAsJSONObject();
        fiveMinPeriod =
                new File("src/test/resources/examples of alpha vantage results/googl-5min")
                        .getFileAsJSONObject();
    }

    @Test
    void testGetDailyStockPricesFromLast100Days() {
        Mockito.when(stockMarketDao.getDailyStockPricesFromLast100Days("", GOOGL)).thenReturn(daily);

        Assertions.assertEquals(daily, stockMarketDao.getDailyStockPricesFromLast100Days("", GOOGL));
    }

    @Test
    void testGetHourPeriodStockPrices() {
        Mockito.when(stockMarketDao.getHourPeriodStockPrices("", GOOGL)).thenReturn(hourPeriod);

        Assertions.assertEquals(hourPeriod, stockMarketService.getHourPeriodStockPrices("", GOOGL));
    }

    @Test
    void testGet5minPeriodStockPrices() {
        Mockito.when(stockMarketDao.get5minPeriodStockPrices("", GOOGL)).thenReturn(fiveMinPeriod);

        Assertions.assertEquals(fiveMinPeriod, stockMarketService.get5minPeriodStockPrices("", GOOGL));
    }
}
