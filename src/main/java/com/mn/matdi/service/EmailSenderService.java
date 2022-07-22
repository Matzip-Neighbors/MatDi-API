package com.mn.matdi.service;

import com.mn.matdi.dto.EmailVerificationNumberDto;
import com.mn.matdi.dto.EmailVerifyRequestDto;
import com.mn.matdi.dto.EmailVerifyResponseDto;
import com.mn.matdi.mapper.EmailVerify;
import com.mn.matdi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final EmailVerify emailVerify;
    private final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;

    @Value("${spring.mail.username}")
    private String username;

    @Async
    public EmailVerifyResponseDto sendVerifyEmailToUser(EmailVerifyRequestDto emailVerifyRequestDto) throws MessagingException {
        Random random = new Random();
        int emailVerifyNumber = random.nextInt(888888) + 111111;

        String fromMail = username;
        String toMail = emailVerifyRequestDto.getEmail();
        String title = "회원가입 인증 이메일 입니다.";
        String content =
                "홈페이지를 방문해주셔서 감사합니다." +
                        "<br><br>" +
                        "인증 번호는 " + emailVerifyNumber + "입니다." +
                        "<br>" +
                        "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";

        EmailVerifyResponseDto emailVerifyResponseDto = EmailVerifyResponseDto.builder()
                .id(1L)
                .email(toMail)
                .vrfNo(Integer.toString(emailVerifyNumber))
                .vrfStatCd("0010")  // 인증유형코드 (이메일: 0010, 문자: 0020)
                .vrfTpCd("0010")    // 인증상태코드 (미인증: 0010, 인증: 0020, 인증실패: 0030)
                .build();

        emailVerify.insertEmailVerificationInfo(emailVerifyResponseDto);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom(fromMail);
        helper.setTo(toMail);
        helper.setSubject(title);
        helper.setText(content,true);
        javaMailSender.send(message);

        return emailVerifyResponseDto;
    }

    public boolean verificationEmailNumber(EmailVerificationNumberDto emailVerificationNumberDto) {
         if (emailVerify.checkEmailVerificationInfo(emailVerificationNumberDto)) {
             emailVerify.updateEmailStat(emailVerificationNumberDto);
             return true;
         }
         return false;
    }
}
