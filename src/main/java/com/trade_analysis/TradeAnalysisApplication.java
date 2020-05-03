package com.trade_analysis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jimmoores.quandl.MetaDataRequest;
import com.jimmoores.quandl.SearchRequest;
import com.jimmoores.quandl.SearchResult;
import com.jimmoores.quandl.classic.ClassicQuandlSession;
import com.trade_analysis.dao.StockMarketDao;
import com.trade_analysis.dao.UserDbDao;
import com.trade_analysis.service.StockMarketService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import static com.trade_analysis.model.StockSymbol.*;

@SpringBootApplication
public class TradeAnalysisApplication {
	public static void main(String[] args) {
		// Start
		ConfigurableApplicationContext run = SpringApplication.run(TradeAnalysisApplication.class, args);
		LoggerFactory.getLogger(TradeAnalysisApplication.class).info("\n\n");

		// Show all users at the beginning of an application
		System.out.println("=".repeat(200));
		UserDbDao userDao = run.getBean("userDbDao", UserDbDao.class);
		userDao.findAll().forEach(System.out::println);
		System.out.println("#".repeat(200));
		// #############################################


		// #############################################
	}
}
