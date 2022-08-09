package com.mn.matdi.service;

import com.mn.matdi.dto.emailSender.EmailSenderDto;
import com.mn.matdi.dto.userVerification.UserVerification;
import com.mn.matdi.dto.userVerification.UserVerificationNumberDto;
import com.mn.matdi.mapper.UserVerificationMapper;
import com.mn.matdi.utils.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserVerificationService {

    private final EmailSender emailSender;
    private final UserVerificationMapper userVerificationMapper;
    private final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;

    @Value("${spring.mail.username}")
    private String fromMail;

    public UserVerification.Response sendUserVerificationNumber(
            UserVerification.Request userVerificationRequestDto
    ) throws MessagingException {

        String userVerificationNumber = getVerificationNumber();

        String toMail = userVerificationRequestDto.getEmail();
        String vrfTpCd = "0010";                                // 인증유형코드 (이메일: 0010, 문자: 0020)
        String vrfStatCd = "0010";                              // 인증상태코드 (미인증: 0010, 인증: 0020, 인증실패: 0030)

        UserVerification.Info userVerificationInfoDto = UserVerification.Info.builder()
                .email(toMail)
                .vrfNo(userVerificationNumber)
                .vrfStatCd(vrfStatCd)
                .vrfTpCd(vrfTpCd)
                .build();

        userVerificationMapper.insertUserVerificationInfo(userVerificationInfoDto);

        String emailSenderReturnMessage = sendEmailToUser(userVerificationNumber, toMail);

        return new UserVerification.Response(
                toMail,
                userVerificationNumber,
                vrfTpCd,
                vrfStatCd,
                emailSenderReturnMessage
        );
    }

    private String getVerificationNumber() {
        Random random = new Random();
        return  Integer.toString(random.nextInt(888888) + 111111);
    }

    private String sendEmailToUser(String userVerificationNumber, String toMail) {

        String title = "회원가입 인증 이메일 입니다.";
        String content =
                "홈페이지를 방문해주셔서 감사합니다." +
                        "<br><br>" +
                        "인증 번호는 " + userVerificationNumber + "입니다." +
                        "<br>" +
                        "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";

        try {
            emailSender.emailSender(EmailSenderDto.builder()
                    .fromMail(fromMail)
                    .toMail(toMail)
                    .title(title)
                    .content(content)
                    .build());

        } catch (MessagingException messagingException) {
            System.out.println(messagingException.getMessage());
            return "이메일 전송을 실패했습니다.";
        }

        return "이메일 전송이 완료되었습니다.";
    }

    public UserVerification.Response verificationEmailNumber(
            UserVerificationNumberDto userVerificationNumberDto
    ) {
        UserVerification.Response userVerificationResponseDto = userVerificationMapper.
                                                                checkUserVerificationNumber(userVerificationNumberDto)
                .orElseThrow(
                        () -> new NullPointerException("데이터가 없습니다.")
                );

         userVerificationMapper.updateEmailStat(userVerificationNumberDto);

         return userVerificationResponseDto;
    }
}
