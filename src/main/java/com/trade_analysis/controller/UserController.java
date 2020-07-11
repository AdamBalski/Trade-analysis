package com.trade_analysis.controller;

import com.trade_analysis.dtos.UserSignUpDto;
import com.trade_analysis.dtos_validation.UserSignUpValidationResult;
import com.trade_analysis.dtos_validation.UserSignUpValidator;
import com.trade_analysis.exception.EmailVerificationTokenNotFoundException;
import com.trade_analysis.exception.UserNotFoundException;
import com.trade_analysis.model.EmailVerificationToken;
import com.trade_analysis.model.User;
import com.trade_analysis.service.UserService;
import com.trade_analysis.util.StringWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.persistence.NonUniqueResultException;
import java.util.UUID;

import static com.trade_analysis.dtos_validation.UserSignUpValidationResult.*;
import static com.trade_analysis.model.UserRole.USUAL;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    @PreAuthorize(value = "permitAll()")
    public String getMainPage(Model model) throws UserNotFoundException {
        if(isAuthenticated()) {
            User user = userService.getUserByUsername(getUsername());
            String greeting = String.format("Hello, %s!", user.getUsername());
            model.addAttribute("greeting", greeting);
            model.addAttribute("admin", user.getUserRole() != USUAL);
        }
        else model.addAttribute("admin", false);

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
    public String signUp(@ModelAttribute UserSignUpDto user, Model model) throws MessagingException {
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

            model.addAttribute("username", user.getUsername());
            return "signed-up";
        }

        model.addAttribute("user", user);
        return "sign-up";
    }

    // todo delete before deployment
    @PreAuthorize(value = "permitAll()")
    @GetMapping(value = "/signed-up")
    public String getSignedUpPage() {
        return "signed-up";
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
        User user = userService.getUserByUsername(getUsername());
        userService.deleteUserById(user.getId());

        SecurityContextHolder.getContext().setAuthentication(null);

        return "index";
    }

    @PreAuthorize(value = "permitAll()")
    @GetMapping(value = "/email-verification/{tokenId}")
    public String getEmailVerification(Model model,
                                       @PathVariable(value = "tokenId") UUID id) {
        model.addAttribute("emailAddress", new StringWrapper(""));
        model.addAttribute("uuid", id);

        try {
            //noinspection unused - It has to be there, because it can throw an exception, so we now if the token exists
            EmailVerificationToken emailVerificationToken = userService.getEmailVerificationToken(id);

            model.addAttribute("showForm", true);
        } catch (EmailVerificationTokenNotFoundException e) {
            model.addAttribute("showForm", false);
            model.addAttribute(
                    "error" ,
                    "The link is outdated, used, corrupt or wasn't never created.</br>" +
                            "Sorry but you can't verify your e-mail by this link");
        }

        return "email-verification";
    }

    @PreAuthorize(value = "permitAll()")
    @PostMapping(value = "/email-verification/{tokenId}")
    public String emailVerification(Model model,
                                    @PathVariable(value = "tokenId") UUID id,
                                    @ModelAttribute(value = "emailAddress") StringWrapper emailAddress) throws UserNotFoundException {
        model.addAttribute("emailAddress", emailAddress);
        model.addAttribute("uuid", id);

        try {
            EmailVerificationToken emailVerificationToken = userService.getEmailVerificationToken(id);
            UUID userId = emailVerificationToken.getUserId();
            User user = userService.findUserById(userId);
            model.addAttribute("showForm", true);

            if(!emailAddress.getString().equals(user.getEmail())) {
                model.addAttribute("error", "This e-mail is invalid. Try again.");
                return "email-verification";
            }
        } catch (EmailVerificationTokenNotFoundException e) {
            model.addAttribute(
                    "error" ,
                    "The link is outdated, used, corrupt or wasn't never created.</br>" +
                            "Sorry but you can't verify your e-mail by this link.");
            model.addAttribute("showForm", false);
        }

        userService.deleteEmailVerificationToken(id);
        return "email-verified";
    }

    // todo delete before deployment
    @PreAuthorize(value = "permitAll()")
    @PostMapping(value = "/email-verified")
    public String getEmailVerifiedPage() {
        return "email-verified";
    }

    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/update-api-key")
    public String getUpdateApiKeyPage(Model model) {
        model.addAttribute("apiKey", new StringWrapper(""));

        return "update-api-key";
    }

    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/update-api-key")
    public String updateApiKey(Model model, @ModelAttribute(value = "apiKey") StringWrapper apiKey) {
        model.addAttribute("apiKey", new StringWrapper(""));
        userService.updateApiKey(getUsername(), apiKey.getString());

        return "update-api-key";
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping(value = "/admin")
    public String getAdminPage() {
        return "admin";
    }

    private boolean isAuthenticated() {
        return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}