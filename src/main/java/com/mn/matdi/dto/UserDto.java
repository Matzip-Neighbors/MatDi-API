package com.mn.matdi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class UserDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String email;
        private String userNm;
        private String userProfPhotoPath;
    }
}