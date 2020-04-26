package com.trade_analysis.dtos_validation;

import com.trade_analysis.dtos.UserSignUpDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UserSignUpValidatorTest {
    @ParameterizedTest
    @CsvSource({
            "'w3 r2'3 3 sx', 'e@mail.com', 'password', 'password', USERNAME_WRONG",
            "'username', 'e@mail.com', 'password1', 'password', PASSWORDS_DIFFERENT",
            "'wr<>ng username', 'e@mail.com', '//////', '//////', USERNAME_WRONG",
            "'//', 'e@mail.com', 'is all wrong?', 'or is not?', USERNAME_WRONG",
            "'username', 'e@mail.com', 'PASSWORD', 'PASSWORD', SUCCESS"
    })
    public void testWholeValidation(String username, String email, String password1, String password2) {
        UserSignUpDto userSignUpDto = UserSignUpDto.builder()
                .username(username)
                .email(email)
                .password1(password1)
                .password2(password2)
                .build();

        UserSignUpValidator.fullValidator.validate(userSignUpDto);
    }

    @ParameterizedTest
    @CsvSource({
            "'user9nam&*)*(e', SUCCESS",
            "'user'.', USERNAME_NOT_CORRECT",
            "'sh', USERNAME_NOT_CORRECT",
            "'to_long_test_case_0000000000000', USERNAME_NOT_CORRECT",
    })
    public void testUsernameCorrectnessValidation(String username, UserSignUpValidationResult expectedValidationResult) {
        UserSignUpDto userSignUpDto = UserSignUpDto.builder()
                .username(username)
                .build();

        UserSignUpValidationResult validationResult = UserSignUpValidator.usernameValidator.validate(userSignUpDto);


        assertEquals(expectedValidationResult, validationResult);
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "'', '', SUCCESS",
                    "'username', 'username', SUCCESS",
                    "'THOSE_PASSWORDS', 'ARE_DIFFERENT', PASSWORDS_DIFFERENT"
            })
    public void testPassword1EqualsPassword2Validation(String password1, String password2, UserSignUpValidationResult expectedValidationResult) {
        UserSignUpDto userSignUpDto = UserSignUpDto.builder().
                password1(password1).
                password2(password2).
                build();

        UserSignUpValidationResult validationResult = UserSignUpValidator.passwordsShouldBeDifferentValidator.validate(userSignUpDto);


        assertEquals(expectedValidationResult, validationResult);
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "'',PASSWORD_NOT_CORRECT",
                    "'password', SUCCESS",
                    "'', PASSWORD_NOT_CORRECT",
                    "'short', PASSWORD_NOT_CORRECT",
                    "'too_long_test_case_000000000000000000000000000000000', PASSWORD_NOT_CORRECT"
            })
    public void testPasswordCorrectnessValidation(String password, UserSignUpValidationResult expectedValidationResult) {
        UserSignUpDto userSignUpDto = UserSignUpDto.builder().password1(password).build();
        UserSignUpValidationResult validationResult = UserSignUpValidator.passwordValidator.validate(userSignUpDto);

        assertEquals(expectedValidationResult, validationResult);
    }
}