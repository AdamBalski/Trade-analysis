package com.trade_analysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TradeAnalysisApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(TradeAnalysisApplication.class, args);
	}
}