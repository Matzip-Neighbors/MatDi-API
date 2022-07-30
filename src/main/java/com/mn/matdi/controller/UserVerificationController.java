package com.mn.matdi.controller;

import com.mn.matdi.dto.userVerification.UserVerificationNumberDto;
import com.mn.matdi.dto.userVerification.UserVerificationRequestDto;
import com.mn.matdi.dto.userVerification.UserVerificationResponseDto;
import com.mn.matdi.service.UserVerificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;


@RestController
@RequiredArgsConstructor
public class UserVerificationController {

    private final UserVerificationService emailSenderService;

    @Operation(summary = "이메일 인증번호 요청")
    @PostMapping("/api/userVerificationNumber")
    public UserVerificationResponseDto sendUserVerificationNumber(
            @RequestBody UserVerificationRequestDto emailVerifyRequestDto
    ) throws MessagingException {
        return emailSenderService.sendUserVerificationNumber(emailVerifyRequestDto);
    }

    @Operation(summary = "이메일 인증번호 검증")
    @PostMapping("/api/userVerificationNumber/check")
    public Boolean mailCheckNumber(@RequestBody UserVerificationNumberDto emailVerificationNumberDto) {
        return emailSenderService.verificationEmailNumber(emailVerificationNumberDto);
    }
}
