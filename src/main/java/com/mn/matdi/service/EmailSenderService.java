package com.mn.matdi.service;

import com.mn.matdi.dto.EmailRequestDto;
import com.mn.matdi.dto.EmailVerificationNumberDto;
import com.mn.matdi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final UserMapper userMapper;
    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;

    @Value("${spring.mail.username}")
    private String username;

    @Async
    public String sendEmail(EmailRequestDto.request emailRequestDto) throws MessagingException {
        Random random = new Random();
        int authenticationRandomNumber = random.nextInt(888888) + 111111;
        log.info("randomNumber ={}", authenticationRandomNumber);

        String fromMail = username;
        System.out.println(emailRequestDto.getEmail());
        String toMail = emailRequestDto.getEmail();
        String title = "회원가입 인증 이메일 입니다.";
        String content =
                "홈페이지를 방문해주셔서 감사합니다." +
                        "<br><br>" +
                        "인증 번호는 " + authenticationRandomNumber + "입니다." +
                        "<br>" +
                        "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";

        EmailRequestDto.request emailRequestDto1 = EmailRequestDto.request.builder()
                .id(1L)
                .email(toMail)
                .vrfNo(Integer.toString(authenticationRandomNumber))
                .vrfStatCd("0010")
                .vrfTpCd("0010")
                .build();

        userMapper.insertEmailVerificationInfo(emailRequestDto1);


        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom(fromMail);
        helper.setTo(toMail);
        helper.setSubject(title);
        helper.setText(content,true);
        javaMailSender.send(message);

        return Integer.toString(authenticationRandomNumber);
    }

    public boolean verificationEmailNumber(EmailVerificationNumberDto emailVerificationNumberDto) {
         if (userMapper.checkEmailVerificationInfo(emailVerificationNumberDto)) {
             userMapper.updateEmailStat(emailVerificationNumberDto);
             return true;
         }
         return false;
    }
}
