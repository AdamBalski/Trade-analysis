package com.trade_analysis.service;

import com.trade_analysis.dtos.UserSignUpDto;
import com.trade_analysis.model.EmailVerificationToken;
import com.trade_analysis.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import javax.mail.MessagingException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

class MailSenderServiceTest {
    private MailSenderService mailSenderService;

    private EmailVerificationToken emailVerificationToken;
    private User user;
    private UUID uuid;

    @BeforeEach
    void init() {
        mailSenderService = mock(MailSenderService.class);

        user = User.valueOf(UserSignUpDto.builder()
                .username("USERNAME")
                .email("email@email.email")
                .password1("").password2("")
                .build());
        uuid = UUID.fromString("c1e9948b-f91e-4656-b486-f34398f01d56");

        emailVerificationToken = new EmailVerificationToken(uuid, user);
    }

    @Test
    void testSendVerificationEmail() {
        try {
            doCallRealMethod().when(mailSenderService).sendVerificationEmail(emailVerificationToken);
            doNothing().when(mailSenderService).sendEmail(any(String.class), any(String.class), any(String.class));

            mailSenderService.sendVerificationEmail(emailVerificationToken);

            verify(mailSenderService).sendEmail(
                    "email@email.email",
                    "<style>*{font-family: arial;}h1{font-size: 3em;}h2{font-size: 2em;}h3{font-size: 1.75em;}</style>" +
                            "<img src='https://i.imgur.com/TKaIejB.png' alt='Logo' title='Logo' style='display:block;' width='400px' height='400px' />" +
                            "<h1>Hi, USERNAME!</h1>" +
                            "<h2>Enter <a href='http://localhost:8080/email-verification/c1e9948b-f91e-4656-b486-f34398f01d56' target='_blank'>this link</a> to confirm that e-mail you have written is actually yours. If you haven't registered yourself, then ignore it.</h2>" +
                            "<h3 >Greetings, trade analysis team.</h3>" +
                            "<h4>Don't reply to this e-mail. It was automatically generated.</h4>",
                    "Email verification for trade analysis.");
        } catch (MessagingException e) {
            fail(e);
            e.printStackTrace();
        }
    }

    @Test
    void testSendVerificationEmailWhenJavaMailSenderThrowsMessagingException() {
        try {
            doCallRealMethod().when(mailSenderService).sendVerificationEmail(emailVerificationToken);
            doThrow(MessagingException.class).when(mailSenderService).sendEmail(any(String.class), any(String.class), any(String.class));

            Executable executable = () -> mailSenderService.sendVerificationEmail(emailVerificationToken);

            Assertions.assertThrows(MessagingException.class, executable);

            verify(mailSenderService).sendEmail(
                    "email@email.email",
                    "<style>*{font-family: arial;}h1{font-size: 3em;}h2{font-size: 2em;}h3{font-size: 1.75em;}</style>" +
                            "<img src='https://i.imgur.com/TKaIejB.png' alt='Logo' title='Logo' style='display:block;' width='400px' height='400px' />" +
                            "<h1>Hi, USERNAME!</h1>" +
                            "<h2>Enter <a href='http://localhost:8080/email-verification/c1e9948b-f91e-4656-b486-f34398f01d56' target='_blank'>this link</a> to confirm that e-mail you have written is actually yours. If you haven't registered yourself, then ignore it.</h2>" +
                            "<h3 >Greetings, trade analysis team.</h3>" +
                            "<h4>Don't reply to this e-mail. It was automatically generated.</h4>",
                    "Email verification for trade analysis.");


        } catch (MessagingException e) {
            fail(e);
            e.printStackTrace();
        }
    }

}