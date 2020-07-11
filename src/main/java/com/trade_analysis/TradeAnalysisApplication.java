package com.trade_analysis;

import com.trade_analysis.dao.EmailVerificationTokenDbDao;
import com.trade_analysis.dao.UserDbDao;
import com.trade_analysis.logs.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
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
		showAllUsers();
		deleteOutdatedTokensWithRelatedUsers();
	}

	private static void showAllUsers() {
		logger.info(TradeAnalysisApplication.class, "Show all users");

		UserDbDao userDao = ctx.getBean("userDbDao", UserDbDao.class);
		userDao.findAll().forEach(user -> logger.info(TradeAnalysisApplication.class, user.toString()));

		logger.info(TradeAnalysisApplication.class, "ending of 'Show all users'");
	}

	// Everyday at midnight (or when executed)
	@Scheduled(cron = "0 0 0 * * ?")
	private static void deleteOutdatedTokensWithRelatedUsers() {
		final int affectedRows = ctx.getBean("emailVerificationTokenDbDao", EmailVerificationTokenDbDao.class)
				.deleteOutdatedTokensWithRelatedUsers();

		logger.info(TradeAnalysisApplication.class,
				"Deleted outdated email verification tokens with assigned users. Affected rows: " + affectedRows + ".");
	}
}
