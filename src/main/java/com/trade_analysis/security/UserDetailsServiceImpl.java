package com.trade_analysis.security;

import com.trade_analysis.dao.UserDbDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    @Qualifier("userDbDao")
    private UserDbDao userDbDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // It loads user, and catches errors (saves exception if NonUniqueResultException is thrown)
        Optional<com.trade_analysis.model.User> userOptional;

        userOptional = userDbDao.getSingleResultByUsername(username);


        com.trade_analysis.model.User user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("Please, check your username"));

        return new User(username, user.getPassword(), user.getGrantedAuthorities());
    }
}
