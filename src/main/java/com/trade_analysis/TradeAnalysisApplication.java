package com.trade_analysis;

import com.trade_analysis.dao.UserDbDao;
import com.trade_analysis.logs.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TradeAnalysisApplication {
	public static final String INTERNET_ADDRESS = "http://localhost:8080/";
	public static final String LOGO_LINK = "https://i.imgur.com/TKaIejB.png";

	public static void main(String[] args) {
		// Start
		ConfigurableApplicationContext run = SpringApplication.run(TradeAnalysisApplication.class, args);
		// Logger
		Logger logger = run.getBean("slf4jLogger", Logger.class);

		// Show all users at the start of the application
		System.out.print("\n\n\n");
		logger.info(TradeAnalysisApplication.class, "Show all users\n");

		UserDbDao userDao = run.getBean("userDbDao", UserDbDao.class);
		userDao.findAll().forEach(System.out::println);

		System.out.println();
		logger.info(TradeAnalysisApplication.class, "ending of 'Show all users'\n\n");
	}
}
