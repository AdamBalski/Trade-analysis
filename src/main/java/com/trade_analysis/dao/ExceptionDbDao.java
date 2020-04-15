package com.trade_analysis.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Qualifier("exceptionDbDao")
@Repository
public class ExceptionDbDao implements ExceptionDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void save(Exception e) {
        StringBuilder stackTrace = new StringBuilder();
        Arrays.stream(e.getStackTrace()).forEach(stackTrace::append);

        jdbcTemplate.update(
                "INSERT INTO Exceptions (id, timestamp, description, stack_trace) VALUES (?, NOW(), ?, ?)",
                size() + 1, e.toString(), stackTrace.toString());
    }

    @Override
    public int size() {
        return jdbcTemplate.query(
                "SELECT COUNT(*) FROM Exceptions",
                (rs, i) -> rs.getInt("COUNT(*)")).get(0);
    }
}
