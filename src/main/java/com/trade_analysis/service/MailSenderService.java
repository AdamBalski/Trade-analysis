package com.trade_analysis.service;

import com.trade_analysis.TradeAnalysisApplication;
import com.trade_analysis.model.EmailVerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component("mailSenderService")
public class MailSenderService {
    private final JavaMailSenderImpl javaMailSender;

    @Autowired
    public MailSenderService(JavaMailSenderImpl javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendVerificationEmail(EmailVerificationToken token) throws MessagingException {
        String username = token.getUser().getUsername();

        String subject = "Email verification for trade analysis.";
        String content = String.format(
                "<style>*{font-family: arial;}h1{font-size: 3em;}h2{font-size: 2em;}h3{font-size: 1.75em;}</style>" +
                        "<img src='%s' alt='Logo' title='Logo' style='display:block;' width='400px' height='400px' />" +
                        "<h1>Hi, %s!</h1>" +
                        "<h2>Enter <a href='%s' target='_blank'>this link</a> to confirm that e-mail you have written is actually yours. If you haven't registered yourself, then ignore it.</h2>" +
                        "<h3 >Greetings, trade analysis team.</h3>" +
                        "<h4>Don't reply to this e-mail. It was automatically generated.</h4>",
                TradeAnalysisApplication.LOGO_LINK,
                username,
                token.getLink());

        sendEmail(token.getUser().getEmail(), content, subject);
    }

    public void sendEmail(String to, String content, String subject) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setTo(to);
        helper.setText(content, true);
        helper.setSubject(subject);

        javaMailSender.send(mimeMessage);
    }
}