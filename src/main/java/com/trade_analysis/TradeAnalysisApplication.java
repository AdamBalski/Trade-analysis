package com.trade_analysis;

import com.trade_analysis.dao.UserDbDao;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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
