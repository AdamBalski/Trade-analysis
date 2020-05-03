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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NonUniqueResultException;
import java.util.UUID;

import static com.trade_analysis.dtos_validation.UserSignUpValidationResult.*;

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

    @PreAuthorize(value = "permitAll()")
    @GetMapping(value = "/sign-up")
    public String getSignUpPage(Model model) {
        model.addAttribute("user", UserSignUpDto.empty());
        model.addAttribute("message", "");
        return "sign-up";
    }

    @PreAuthorize(value = "permitAll()")
    @PostMapping(value = "/sign-up")
    public String signUp(@ModelAttribute UserSignUpDto user, Model model) throws IllegalArgumentException, DataIntegrityViolationException {
        UserSignUpValidationResult result = UserSignUpValidator.fullValidator.validate(user);

        if(!result.isSuccess()) {
            model.addAttribute("user", user);

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

            return "sign-up";
        }
        else if(userService.existsByUsername(user.getUsername())) {
            model.addAttribute("message", "Username is taken");

            return "sign-up";
        }
        else if(userService.existsByEmail(user.getEmail())) {
            model.addAttribute("message", "E-mail is taken");

            return "sign-up";
        }
        else {
            userService.signUp(user);
            return "signed-up";
        }
    }

    @PreAuthorize(value = "permitAll()")
    @GetMapping(value = "/login")
    public String getLoginPage(@RequestParam(name = "logout", defaultValue = "false") boolean logout,
                               Model model,
                               CsrfToken csrfToken) {
        model.addAttribute("logout", logout);
        model.addAttribute("csrf", csrfToken.getToken());

        return "login";
    }

    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/logout")
    public String getLogoutPage(Model model, CsrfToken csrfToken) {
        model.addAttribute("csrf", csrfToken.getToken());
        return "logout";
    }

    private boolean isAuthenticated() {
        return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    private String getName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}