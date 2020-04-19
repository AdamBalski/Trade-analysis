package com.trade_analysis.security;

import com.trade_analysis.dao.ExceptionDao;
import com.trade_analysis.dao.UserDbDao;
import com.trade_analysis.exception.UsernameNotUniqueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.NonUniqueResultException;
import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    @Qualifier("exceptionDbDao")
    ExceptionDao exceptionDao;

    @Autowired
    @Qualifier("userDbDao")
    UserDbDao userDbDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // It loads user, and catches errors (saves exception if NonUniqueResultException is thrown)
        Optional<com.trade_analysis.model.User> userOptional;

        try {
            userOptional = userDbDao.getSingleResultByUsername(username);

        }
        catch (NonUniqueResultException e) {
            e.printStackTrace();
            exceptionDao.save(new UsernameNotUniqueException());
            throw new UsernameNotFoundException("We have some problems. Sorry, try again later.");
        }

        com.trade_analysis.model.User user =
                userOptional
                .orElseThrow(() -> new UsernameNotFoundException("Please, check your username"));

        return new User(username, user.getPassword(), user.getGrantedAuthorities());
    }
}
