package com.mn.matdi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class KakaoUser {

    @Getter
    public static class AuthCode {
        private String code;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private String token;
        private Long userId;
        private String email;
        private String nickname;
        private String profileImage;
    }
}
