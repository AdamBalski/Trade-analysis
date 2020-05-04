package com.trade_analysis.controller;

import com.trade_analysis.dtos.UserSignUpDto;
import com.trade_analysis.dtos_validation.UserSignUpValidationResult;
import com.trade_analysis.dtos_validation.UserSignUpValidator;
import com.trade_analysis.exception.UserNotFoundException;
import com.trade_analysis.exception.UsernameNotUniqueException;
import com.trade_analysis.model.User;
import com.trade_analysis.service.UserService;
import org.apache.tomcat.util.descriptor.web.ContextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NonUniqueResultException;
import java.security.Security;
import java.util.Arrays;
import java.util.UUID;

import static com.trade_analysis.dtos_validation.UserSignUpValidationResult.*;

@Controller
@SuppressWarnings(value = "unused")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/")
    @PreAuthorize(value = "permitAll()")
    public String getMainPage(Model model) {
        if(isAuthenticated()) {
            String greeting = String.format("Hello, %s!", getName());
            model.addAttribute("greeting", greeting);
        }

        return "index";
    }

    @GetMapping(value = "/me")
    @PreAuthorize(value = "isAuthenticated()")
    public String getMyData(Model model) throws UserNotFoundException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", userService.getUserByUsername(name));

        return "me";
    }

    @GetMapping(value = "users")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
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

    @PreAuthorize(value = "permitAll()")
    @GetMapping(value = "/sign-up")
    public String getSignUpPage(Model model) {
        model.addAttribute("user", UserSignUpDto.empty());
        model.addAttribute("message", "");
        return "sign-up";
    }

    @PreAuthorize(value = "permitAll()")
    @PostMapping(value = "/sign-up")
    public String signUp(@ModelAttribute UserSignUpDto user, Model model) {
        UserSignUpValidationResult result = UserSignUpValidator.fullValidator.validate(user);

        if(!result.isSuccess()) {
            String message = "Something went wrong";
            if(result == PASSWORD_NOT_CORRECT) {
                message = "Password isn't correct";
            }
            else if(result == PASSWORDS_DIFFERENT) {
                message = "Passwords aren't equal";
            }
            else if(result == USERNAME_NOT_CORRECT) {
                message = "Username isn't correct";
            }

            model.addAttribute("message", message);
        }
        else if(userService.existsByUsername(user.getUsername())) {
            model.addAttribute("message", "Username is taken");
        }
        else if(userService.existsByEmail(user.getEmail())) {
            model.addAttribute("message", "E-mail is taken");
        }
        else {
            userService.signUp(user);
            return "login";
        }

        model.addAttribute("user", user);
        return "sign-up";
    }

    @PreAuthorize(value = "permitAll()")
    @GetMapping(value = "/login")
    public String getLoginPage(@RequestParam(name = "logout", defaultValue = "false") boolean logout,
                               @RequestParam(name = "error", defaultValue = "false") boolean error,
                               Model model,
                               CsrfToken csrfToken) {
        model.addAttribute("logout", logout);
        model.addAttribute("error", error);

        model.addAttribute("csrf", csrfToken.getToken());

        return "login";
    }

    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/logout")
    public String getLogoutPage(Model model, CsrfToken csrfToken) {
        model.addAttribute("csrf", csrfToken.getToken());
        return "logout";
    }


    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/delete")
    public String getDeletePage() {
        return "delete";
    }

    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/delete")
    public String delete() throws UserNotFoundException {
        User user = userService.getUserByUsername(getName());
        userService.deleteUserById(user.getId());

        SecurityContextHolder.getContext().setAuthentication(null);

        return "index";
    }

    private boolean isAuthenticated() {
        return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    private String getName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}