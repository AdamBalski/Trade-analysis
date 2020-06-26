package com.trade_analysis.service;

import com.trade_analysis.dao.StockMarketDao;
import com.trade_analysis.dtos.StockQueryDto;
import com.trade_analysis.exception.InvalidApiCallException;
import com.trade_analysis.exception.TooManyApiCallsException;
import com.trade_analysis.model.StockPrices;
import com.trade_analysis.util.File;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static com.trade_analysis.model.StockSymbol.AMZN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
                new File("src/test/resources/examples of alpha vantage results/amzn-1day")
                        .getFileAsJSONObject();
        hourPeriod =
                new File("src/test/resources/examples of alpha vantage results/amzn-1hour")
                        .getFileAsJSONObject();
        fiveMinPeriod =
                new File("src/test/resources/examples of alpha vantage results/amzn-5min")
                        .getFileAsJSONObject();
    }

    @Test
    void testGetStockPricesFromStockQueryDto() {
        String apiKey = "123456789ABCDEFG";
        StockQueryDto stockQueryDto = StockQueryDto.builder()
                .apiKey(apiKey)
                .period("FIVE_MINUTES_PERIOD")
                .symbol("AMZN")
                .build();

        StockMarketService stockMarketService = mock(StockMarketService.class);

        try {
            when(stockMarketService.getStockPricesFromStockQueryDto(any())).thenCallRealMethod();
            when(stockMarketService.getRawFromStockQueryDto(stockQueryDto)).thenReturn(fiveMinPeriod);

            StockPrices expected = new StockPrices(fiveMinPeriod);
            StockPrices actual = stockMarketService.getStockPricesFromStockQueryDto(stockQueryDto);

            assertEquals(expected, actual);
        } catch (InvalidApiCallException | TooManyApiCallsException e) {
            fail(e);
            e.printStackTrace();
        }
    }

    @Test
    void testGetRawFromStockQueryDto() {
        String apiKey = "1234567890ABCDEFG";
        StockQueryDto stockQueryDto = StockQueryDto.builder()
                .apiKey(apiKey)
                .period("FIVE_MINUTES_PERIOD")
                .symbol("AMZN")
                .build();

        when(stockMarketDao.get5minPeriodStockPrices(apiKey, AMZN)).thenReturn(fiveMinPeriod);

        assertEquals(fiveMinPeriod, stockMarketService.getRawFromStockQueryDto(stockQueryDto));
    }

    @Test
    void testGetDailyStockPricesFromLast100Days() {
        when(stockMarketDao.getDailyStockPricesFromLast100Days("", AMZN)).thenReturn(daily);

        assertEquals(daily, stockMarketDao.getDailyStockPricesFromLast100Days("", AMZN));
    }

    @Test
    void testGetHourPeriodStockPrices() {
        when(stockMarketDao.getHourPeriodStockPrices("", AMZN)).thenReturn(hourPeriod);

        assertEquals(hourPeriod, stockMarketService.getHourPeriodStockPrices("", AMZN));
    }

    @Test
    void testGet5minPeriodStockPrices() {
        when(stockMarketDao.get5minPeriodStockPrices("", AMZN)).thenReturn(fiveMinPeriod);

        assertEquals(fiveMinPeriod, stockMarketService.get5minPeriodStockPrices("", AMZN));
    }
}
