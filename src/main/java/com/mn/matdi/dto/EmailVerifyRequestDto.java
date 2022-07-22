package com.mn.matdi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerifyRequestDto {
    private Long id;
    private String email;
    private String vrfNo; //인증번호
    private String vrfTpCd; //이메일 0010 ,문자 0020
    private String vrfStatCd; //미인증 0010, 인증 0020, 인증실패 0030
}
