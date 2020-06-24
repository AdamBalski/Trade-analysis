package com.trade_analysis.logs;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component("slf4jLogger")
public class Slf4jLogger implements Logger {
    @Override
    public void save(Class c, Exception e) {
        StringBuilder stackTrace = new StringBuilder();
        Arrays.stream(e.getStackTrace()).forEach(obj -> stackTrace.append("\n" + obj));

        LoggerFactory.getLogger(c).error(stackTrace.toString());
    }

    @Override
    public void info(Class c, String s) {
        LoggerFactory.getLogger(c).info(s);
    }

    @Override
    public void emptyLine(Class c) {
        LoggerFactory.getLogger(c).info("\n");
    }
}
