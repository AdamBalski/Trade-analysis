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

	private static ConfigurableApplicationContext ctx;
	private static Logger logger;

	public static void main(String[] args) {
		ctx = SpringApplication.run(TradeAnalysisApplication.class, args);
		logger = getLogger();

		postConstruct();
	}

	private static Logger getLogger() {
		return ctx.getBean("slf4jLogger", Logger.class);
	}

	private static void postConstruct() {
		System.out.println();

		showAllUsers();
		deleteOutdatedTokensWithRelatedUsers();
	}

	private static void showAllUsers() {
		logger.info(TradeAnalysisApplication.class, "Show all users");

		UserDbDao userDao = ctx.getBean("userDbDao", UserDbDao.class);
		userDao.findAll().forEach(user -> logger.info(TradeAnalysisApplication.class, user.toString()));

		logger.info(TradeAnalysisApplication.class, "ending of 'Show all users'\n\n");
	}

	private static void deleteOutdatedTokensWithRelatedUsers() {
		ctx.getBean("emailVerificationTokenDbDao", EmailVerificationTokenDbDao.class)
				.deleteOutdatedTokensWithRelatedUsers();
	}
}
