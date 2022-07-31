package com.mn.matdi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class User {
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
