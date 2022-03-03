package com.mn.matdi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class KakaoUser {

    @Getter
    @Builder
    @AllArgsConstructor
    public class Response {
        private String token;
        private Long userId;
        private String email;
        private String nickname;
        private String profileImage;
    }
}
