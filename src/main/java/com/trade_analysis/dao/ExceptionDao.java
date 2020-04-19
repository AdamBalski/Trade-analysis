package com.trade_analysis.dao;

// Purpose of this class is for saving exceptions so developer can read it and correct
public interface ExceptionDao {
    void save(Exception e);
    int size();
}
