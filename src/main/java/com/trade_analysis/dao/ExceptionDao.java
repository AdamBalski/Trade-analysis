package com.trade_analysis.dao;

// The purpose of this interface is for saving exceptions so developer can read it and correct
public interface ExceptionDao {
    void save(Exception e);
    int size();
}
