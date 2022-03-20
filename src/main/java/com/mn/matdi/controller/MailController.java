package com.mn.matdi.controller;

import com.mn.matdi.dto.EmailRequestDto;
import com.mn.matdi.dto.EmailVerificationNumberDto;
import com.mn.matdi.service.EmailSenderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class MailController {

    private final EmailSenderService emailSenderService;

    @Operation(summary = "이메일 인증번호 요청")
    @PostMapping("/api/emailVerificationNumber")
    public String mailSendNumber(@RequestBody EmailRequestDto.request request) {
        return emailSenderService.sendEmail(request);
    }
    
    @Operation(summary = "이메일 인증번호 검증")
    @PostMapping("/api/emailVerificationNumber/check")
    public Boolean mailCheckNumber(@RequestBody EmailVerificationNumberDto request) {
        return emailSenderService.verificationEmailNumber(request);
    }
}
