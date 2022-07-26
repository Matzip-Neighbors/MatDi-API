package com.mn.matdi.controller;

import com.mn.matdi.dto.email.EmailVerificationNumberDto;
import com.mn.matdi.dto.email.EmailVerifyRequestDto;
import com.mn.matdi.dto.email.EmailVerifyResponseDto;
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
    @PostMapping("/api/userVerificationNumber")
    public EmailVerifyResponseDto sendUserVerificationNumber(@RequestBody EmailVerifyRequestDto emailVerifyRequestDto) throws MessagingException {
        return emailSenderService.sendUserVerificationNumber(emailVerifyRequestDto);
    }

    @Operation(summary = "이메일 인증번호 검증")
    @PostMapping("/api/userVerificationNumber/check")
    public Boolean mailCheckNumber(@RequestBody EmailVerificationNumberDto emailVerificationNumberDto) {
        return emailSenderService.verificationEmailNumber(emailVerificationNumberDto);
    }
}
