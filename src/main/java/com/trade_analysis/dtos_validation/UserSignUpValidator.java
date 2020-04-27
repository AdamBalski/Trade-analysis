package com.trade_analysis.dtos_validation;

import com.trade_analysis.dtos.UserSignUpDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.trade_analysis.dtos_validation.UserSignUpValidationResult.*;

/**
 * Fields of this interface validate UserSignUpDto for correctness, but
 * don't check if it is unique in the database
 */
public interface UserSignUpValidator extends Validator<UserSignUpDto, UserSignUpValidationResult> {
    Pattern usernamePattern = Pattern.compile("[A-Za-z0-9!@#$%^&*()_]{4,30}");
    Pattern passwordPattern = Pattern.compile("[A-Za-z0-9!@#$%^&*()_]{8,50}");

    UserSignUpValidator fullValidator = userSignUpDto -> {
        return UserSignUpValidator.usernameValidator
                .and(UserSignUpValidator.passwordValidator)
                .and(UserSignUpValidator.passwordsShouldBeDifferentValidator)
                .validate(userSignUpDto);
    };

    UserSignUpValidator usernameValidator = userSignUpDto -> {
        Matcher matcher = usernamePattern.matcher(userSignUpDto.getUsername());

        return matcher.matches() ?
                SUCCESS : USERNAME_NOT_CORRECT;
    };


    UserSignUpValidator passwordValidator = userSignUpDto -> {
        Matcher matcher = passwordPattern.matcher(userSignUpDto.getPassword1());

        return matcher.matches() ?
                SUCCESS : PASSWORD_NOT_CORRECT;
    };
    UserSignUpValidator passwordsShouldBeDifferentValidator = userSignUpDto -> {
        return userSignUpDto.getPassword1()
                .equals(userSignUpDto.getPassword2()) ?
                SUCCESS : PASSWORDS_DIFFERENT;
    };
}
