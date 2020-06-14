package com.trade_analysis;

import com.trade_analysis.dao.UserDbDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TradeAnalysisApplication {
	public static void main(String[] args) {
		// Start
		ConfigurableApplicationContext run = SpringApplication.run(TradeAnalysisApplication.class, args);

		// Show all users at the start of the application
		System.out.println("=".repeat(200));
		UserDbDao userDao = run.getBean("userDbDao", UserDbDao.class);
		userDao.findAll().forEach(System.out::println);
		System.out.println("#".repeat(200) + "\n\n");
		// #############################################


		// #############################################
	}
}
