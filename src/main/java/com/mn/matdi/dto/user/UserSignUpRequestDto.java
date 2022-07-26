package com.mn.matdi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequestDto {
    private Long id;
    private String email;
    private String userPwd;
    private String userNm;
    private String userProfPhotoPath;
    private String userRegTpCd;
    private String userStatCd;
    private char adminYn;
    private LocalDateTime regDtm;
    private LocalDateTime lastLoginDtm;
}
