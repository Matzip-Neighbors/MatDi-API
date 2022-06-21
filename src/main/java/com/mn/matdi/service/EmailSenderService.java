//package com.mn.matdi.service;
//
//import com.mn.matdi.dto.EmailRequestDto;
//import com.mn.matdi.dto.EmailVerificationNumberDto;
//import com.mn.matdi.mapper.UserMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import javax.mail.internet.MimeMessage;
//import java.util.Random;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class EmailSenderService {
//
//    private final JavaMailSender mailSender;
//    private final UserMapper userMapper;
//    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;
//
//    public String sendEmail(EmailRequestDto.request emailRequestDto) {
//        Random random = new Random();
//        int authenticationRandomNumber = random.nextInt(888888) + 111111;
//        log.info("randomNumber ={}", authenticationRandomNumber);
//
//        String fromMail = "woojin126@naver.com";
//        String toMail = emailRequestDto.getEmail();
//        String title = "회원가입 인증 이메일 입니다.";
//        String content =
//                "홈페이지를 방문해주셔서 감사합니다." +
//                        "<br><br>" +
//                        "인증 번호는 " + authenticationRandomNumber + "입니다." +
//                        "<br>" +
//                        "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
//
//        EmailRequestDto.request emailRequestDto1 = EmailRequestDto.request.builder()
//                .email(toMail)
//                .vrfNo(Integer.toString(authenticationRandomNumber))
//                .vrfStatCd("0010")
//                .vrfTpCd("0010")
//                .build();
//
//        userMapper.insertEmailVerificationInfo(emailRequestDto1);
//
//
//        /** 이메일 전송을 위한 코드 **/
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
//            helper.setFrom(fromMail);
//            helper.setTo(toMail);
//            helper.setSubject(title);
//            helper.setText(content,true);
//            mailSender.send(message);
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//
//        return Integer.toString(authenticationRandomNumber);
//    }
//
//    public boolean verificationEmailNumber(EmailVerificationNumberDto emailVerificationNumberDto) {
//         if (userMapper.checkEmailVerificationInfo(emailVerificationNumberDto)) {
//             userMapper.updateEmailStat(emailVerificationNumberDto);
//             return true;
//         }
//         return false;
//    }
//}
