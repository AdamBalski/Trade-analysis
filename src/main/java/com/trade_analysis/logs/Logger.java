package com.trade_analysis.logs;

import org.springframework.stereotype.Component;

@Component("logger")
public interface Logger {
    void save(Class c, Exception e);

    void info(Class c, String s);

    default void info(Class c, Object o) {
        info(c, o.toString());
    }
}
