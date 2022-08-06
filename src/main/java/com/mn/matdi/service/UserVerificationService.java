package com.mn.matdi.service;

import com.mn.matdi.dto.userVerification.EmailSenderDto;
import com.mn.matdi.dto.userVerification.UserVerificationNumberDto;
import com.mn.matdi.dto.userVerification.UserVerificationRequestDto;
import com.mn.matdi.dto.userVerification.UserVerificationResponseDto;
import com.mn.matdi.mapper.UserVerificationMapper;
import com.mn.matdi.utils.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserVerificationService {

    private final EmailSender emailSender;
    private final UserVerificationMapper userVerificationMapper;
    private final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;

    @Value("${spring.mail.username}")
    private String username;

    public UserVerificationResponseDto sendUserVerificationNumber(
            UserVerificationRequestDto emailVerifyRequestDto
    ) throws MessagingException {
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

        UserVerificationResponseDto userVerificationResponseDto = UserVerificationResponseDto.builder()
                .email(toMail)
                .vrfNo(Integer.toString(emailVerifyNumber))
                .vrfStatCd("0010")  // 인증유형코드 (이메일: 0010, 문자: 0020)
                .vrfTpCd("0010")    // 인증상태코드 (미인증: 0010, 인증: 0020, 인증실패: 0030)
                .build();

        userVerificationMapper.insertUserVerificationInfo(userVerificationResponseDto);

        emailSender.emailSender(EmailSenderDto.builder()
                .fromMail(fromMail)
                .toMail(toMail)
                .title(title)
                .content(content)
                .build());

        return userVerificationResponseDto;
    }

    public UserVerificationResponseDto verificationEmailNumber(
            UserVerificationNumberDto userVerificationNumberDto
    ) {
        UserVerificationResponseDto userVerificationResponseDto = userVerificationMapper.
                                                                checkUserVerificationNumber(userVerificationNumberDto)
                .orElseThrow(
                        () -> new NullPointerException("데이터가 없습니다.")
                );

         userVerificationMapper.updateEmailStat(userVerificationNumberDto);

         return userVerificationResponseDto;
    }
}
