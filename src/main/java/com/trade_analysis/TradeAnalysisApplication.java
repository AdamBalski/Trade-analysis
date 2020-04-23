package com.trade_analysis;

import com.trade_analysis.dao.UserDbDao;
import com.trade_analysis.dtos.UserSignUpDto;
import com.trade_analysis.dtos_validation.UserSignUpValidator;
import com.trade_analysis.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

@SpringBootApplication
public class TradeAnalysisApplication {
	public static void main(String[] args) {
		// Start
		ConfigurableApplicationContext run = SpringApplication.run(TradeAnalysisApplication.class, args);

		// Show all users at the beginning of an application
		System.out.println("=".repeat(200));
		UserDbDao userDao = run.getBean("userDbDao", UserDbDao.class);
		userDao.findAll().forEach(System.out::println);
		System.out.println("#".repeat(200));
	}
}
