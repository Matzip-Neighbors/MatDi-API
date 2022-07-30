package com.mn.matdi.dto.userVerification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVerificationResponseDto {
    private String email;
    private String vrfNo;
    private String vrfTpCd;
    private String vrfStatCd;
    private LocalDateTime exprDtm;
}
