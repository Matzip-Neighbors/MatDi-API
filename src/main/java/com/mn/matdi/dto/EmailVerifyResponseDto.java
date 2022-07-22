package com.mn.matdi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerifyResponseDto {
    private Long id;
    private String email;
    private String vrfNo;
    private String vrfTpCd;
    private String vrfStatCd;
    private LocalDateTime exprDtm;
}
