package com.trade_analysis.controller;

import com.trade_analysis.exception.UserNotFoundException;
import com.trade_analysis.exception.UsernameNotUniqueException;
import com.trade_analysis.model.User;
import com.trade_analysis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.NonUniqueResultException;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/")
    @PreAuthorize(value = "permitAll()")
    public String getMainPage(Model model) {
        if(isAuthenticated()) {
            String greeting = String.format("Hello, %s!", getName());
            model.addAttribute("greeting", greeting);
            System.out.println();
        }

        return "index";
    }

    @GetMapping(value = "/me")
    @PreAuthorize(value = "isAuthenticated()")
    public String getMyData(Model model) throws UserNotFoundException, UsernameNotUniqueException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", userService.getUserByUsername(name));

        return "me";
    }

    @GetMapping(value = "users")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUserLinks());
        return "users";
    }

    @GetMapping(value = "user/{userId}")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public String getUser(@PathVariable(value = "userId") String userId, Model model) throws NonUniqueResultException, UserNotFoundException {
        User user = userService.findUserById(UUID.fromString(userId));
        String title = String.format("User id: %s", userId);

        model.addAttribute("user", user);
        model.addAttribute("title", title);

        return "user";
    }

    private boolean isAuthenticated() {
        return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    private String getName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}