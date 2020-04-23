package com.trade_analysis.dtos_validation;

import com.trade_analysis.dtos.UserSignUpDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Value;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

class UserSignUpValidatorTest {
    @ParameterizedTest
    @CsvSource({
            "'usernam&*)*(e', SUCCESS",
            "'user'.', USERNAME_NOT_CORRECT",
            "'sh', USERNAME_NOT_CORRECT",
            "'to_long_test_case_0000000000000', USERNAME_NOT_CORRECT",
    })
    public void should_validate_username_correctness(String username, UserSignUpValidationResult expectedValidationResult) {
        UserSignUpDto userSignUpDto = UserSignUpDto.builder()
                .username(username)
                .build();

        UserSignUpValidationResult validationResult = UserSignUpValidator.usernameValidator.validate(userSignUpDto);


        assertEquals(expectedValidationResult, validationResult);
    }

    @ParameterizedTest
    @CsvSource(
            emptyValue = "",
            value = {
                    "'','',SUCCESS",
                    "username,username,SUCCESS",
                    "THOSE_PASSWORDS,ARE_DIFFERENT,PASSWORDS_DIFFERENT"
            })
    public void should_validate_that_password1_equals_password2(String password1, String password2, UserSignUpValidationResult expectedValidationResult) {
        UserSignUpDto userSignUpDto = UserSignUpDto.builder().
                password1(password1).
                password2(password2).
                build();

        UserSignUpValidationResult validationResult = UserSignUpValidator.passwordsShouldBeDifferentValidator.validate(userSignUpDto);


        assertEquals(expectedValidationResult, validationResult);
    }

    @ParameterizedTest
    @CsvSource(emptyValue = "",
            value = {
                    "'',PASSWORD_NOT_CORRECT",
                    "'password', SUCCESS",
                    "'', PASSWORD_NOT_CORRECT",
                    "'short', PASSWORD_NOT_CORRECT",
                    "'too_long_test_case_000000000000000000000000000000000', PASSWORD_NOT_CORRECT"
            })
    public void should_validate_password_correctness(String password, UserSignUpValidationResult expectedValidationResult) {
        UserSignUpDto userSignUpDto = UserSignUpDto.builder().password1(password).build();
        UserSignUpValidationResult validationResult = UserSignUpValidator.passwordValidator.validate(userSignUpDto);

        assertEquals(expectedValidationResult, validationResult);
    }
}