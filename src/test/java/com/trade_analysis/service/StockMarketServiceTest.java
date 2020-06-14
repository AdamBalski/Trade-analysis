package com.trade_analysis.service;

import com.trade_analysis.dao.StockMarketDao;
import com.trade_analysis.util.File;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

public class StockMarketService {
    @InjectMocks
    StockMarketService stockMarketService;

    @Mock
    StockMarketDao stockMarketDao;

    JSONObject daily;
    JSONObject hourPeriod;
    JSONObject fiveMinPeriod;

    @BeforeEach
    void init() throws IOException {
        MockitoAnnotations.initMocks(this);

        daily =
                new File("")
                .getFileAsJSONObject();
    }

}
