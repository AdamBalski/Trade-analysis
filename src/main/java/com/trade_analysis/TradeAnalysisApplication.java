package com.trade_analysis;

import com.trade_analysis.dao.EmailVerificationTokenDbDao;
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
		ConfigurableApplicationContext run = SpringApplication.run(TradeAnalysisApplication.class, args);
		System.out.println();

		Logger logger = getLogger(run);
		showAllUsers(run, logger);
		deleteOutdatedTokensWithRelatedUsers(run);
	}

	private static Logger getLogger(ConfigurableApplicationContext run) {
		return run.getBean("slf4jLogger", Logger.class);
	}

	private static void showAllUsers(ConfigurableApplicationContext run, Logger logger) {
		logger.info(TradeAnalysisApplication.class, "Show all users");

		UserDbDao userDao = run.getBean("userDbDao", UserDbDao.class);
		userDao.findAll().forEach(user -> logger.info(TradeAnalysisApplication.class, user.toString()));

		logger.info(TradeAnalysisApplication.class, "ending of 'Show all users'\n\n");
	}

	private static void deleteOutdatedTokensWithRelatedUsers(ConfigurableApplicationContext run) {
		run.getBean("emailVerificationTokenDbDao", EmailVerificationTokenDbDao.class)
				.deleteOutdatedTokensWithRelatedUsers();
	}
}
