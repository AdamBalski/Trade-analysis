package com.trade_analysis;

import com.trade_analysis.dao.UserDao;
import com.trade_analysis.dao.UserDbDao;
import com.trade_analysis.model.User;
import com.trade_analysis.model.UserRole;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class TradeAnalysisApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(TradeAnalysisApplication.class, args);
	}
}