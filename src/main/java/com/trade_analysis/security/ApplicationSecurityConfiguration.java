package com.trade_analysis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.stereotype.Component;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfiguration() {
        this.passwordEncoder = new PasswordEncoderFactory().passwordEncoder();
    }

    @Bean(value = "userDetailsService")
    @Override protected UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Override protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/me").permitAll()
                .antMatchers("/sign-up", "/signed-up").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll()
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/login").permitAll()
                .and()
                .logout().clearAuthentication(true).invalidateHttpSession(true).logoutUrl("/logout").logoutSuccessUrl("/login?logout=true").permitAll();
    }
}

@Component
class PasswordEncoderFactory {
    public PasswordEncoderFactory() {

    }

    @Bean(value = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}