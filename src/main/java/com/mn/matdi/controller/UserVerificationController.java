package com.mn.matdi.controller;

import com.mn.matdi.dto.EmailVerificationNumberDto;
import com.mn.matdi.dto.EmailVerifyRequestDto;
import com.mn.matdi.dto.EmailVerifyResponseDto;
import com.mn.matdi.service.EmailSenderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;


@RestController
@RequiredArgsConstructor
public class UserVerificationController {

    private final EmailSenderService emailSenderService;

    @Operation(summary = "이메일 인증번호 요청")
    @PostMapping("/api/emailVerificationNumber")
    public EmailVerifyResponseDto mailSendNumber(@RequestBody EmailVerifyRequestDto emailVerifyRequestDto) throws MessagingException {
        return emailSenderService.sendVerifyEmailToUser(emailVerifyRequestDto);
    }

    @Operation(summary = "이메일 인증번호 검증")
    @PostMapping("/api/emailVerificationNumber/check")
    public Boolean mailCheckNumber(@RequestBody EmailVerificationNumberDto request) {
        return emailSenderService.verificationEmailNumber(request);
    }
}
