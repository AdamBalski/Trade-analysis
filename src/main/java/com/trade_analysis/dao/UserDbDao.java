package com.trade_analysis.dao;

import com.trade_analysis.exception.IdNotUniqueException;
import com.trade_analysis.exception.UserNotFoundException;
import com.trade_analysis.exception.UsernameNotUniqueException;
import com.trade_analysis.model.User;
import com.trade_analysis.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Qualifier("userDbDao")
public class UserDbDao implements UserDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public static final RowMapper<User> rowMapper = (rs, i) -> new User(
            UUID.fromString(rs.getString("id")),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password"),
            UserRole.valueOf(rs.getString("role"))
    );

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException, UsernameNotUniqueException {
        List<User> users = jdbcTemplate.query("SELECT * FROM Users WHERE username = ?", rowMapper, username);

        if(users.isEmpty()) {
            throw new UserNotFoundException();
        }

        else if(users.size() > 1) {
            throw new UsernameNotUniqueException("");
        }

        return users.get(0);
    }

    @Override
    public List<User> findAllUsers() {
        return jdbcTemplate.query("SELECT * FROM Users;", rowMapper);
    }

    @Override
    public User findUserById(UUID uuid) throws UserNotFoundException, IdNotUniqueException {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM Users WHERE id = ?",
                ps -> ps.setString(1, uuid.toString()),
                rowMapper);

        if(users.isEmpty()) {
            throw new UserNotFoundException();
        }
        else if(users.size() > 1) {
            throw new IdNotUniqueException();
        }

        return users.get(0);
    }
}