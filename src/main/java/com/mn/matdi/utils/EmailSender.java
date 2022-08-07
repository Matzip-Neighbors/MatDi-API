package com.mn.matdi.utils;

import com.mn.matdi.dto.emailSender.EmailSenderDto;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Configuration
@AllArgsConstructor
public class EmailSender {


    private final JavaMailSender javaMailSender;


    public void emailSender(EmailSenderDto emailSenderDto) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom(emailSenderDto.getFromMail());
        helper.setTo(emailSenderDto.getToMail());
        helper.setSubject(emailSenderDto.getTitle());
        helper.setText(emailSenderDto.getContent(),true);
        javaMailSender.send(message);
    }

}
