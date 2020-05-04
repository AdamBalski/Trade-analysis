package com.trade_analysis.controller;

import com.trade_analysis.service.StockMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StockPriceController {
    @Autowired
    StockMarketService stockMarketService;
}
