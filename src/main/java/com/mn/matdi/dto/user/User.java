package com.mn.matdi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


public class User {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Info {
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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
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


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String email;
        private String userNm;
        private String userProfPhotoPath;
    }
}
