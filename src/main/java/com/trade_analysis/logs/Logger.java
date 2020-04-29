package com.trade_analysis.logs;

public interface Logger {
    void save(Class c, Exception e);

    void info(Class c, String s);

    void emptyLine(Class c);
}
