package com.trade_analysis.security;

import com.trade_analysis.dao.ExceptionDao;
import com.trade_analysis.dao.UserDao;
import com.trade_analysis.exception.UserNotFoundException;
import com.trade_analysis.exception.UsernameNotUniqueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    @Qualifier("exceptionDbDao")
    ExceptionDao exceptionDao;

    @Autowired
    @Qualifier("userDbDao")
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.trade_analysis.model.User user;

        try {
            user = userDao.getUserByUsername(username);
        }
        catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("");
        }
        catch (UsernameNotUniqueException e) {
            e.printStackTrace();
            exceptionDao.save(new UsernameNotUniqueException());
            throw new UsernameNotFoundException("");
        }

        return new User(username, user.getPassword(), user.getGrantedAuthorities());
    }
}
