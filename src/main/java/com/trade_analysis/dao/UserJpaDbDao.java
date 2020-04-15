package com.trade_analysis.dao;

import com.trade_analysis.exception.UserNotFoundException;
import com.trade_analysis.exception.UsernameNotUniqueException;
import com.trade_analysis.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier(value = "userJpaDbDao")
public interface UserJpaDbDao {//extends JpaRepository<User, UUID>, UserDao {
    /*@Overrided*/ default User getUserByUsername(String username) throws UserNotFoundException, UsernameNotUniqueException {
        return findUserByUsername(username);
    }
    User findUserByUsername(String username);
}
