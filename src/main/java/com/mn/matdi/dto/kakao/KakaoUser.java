package com.mn.matdi.dto.kakao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class KakaoUser {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Info {
        private String token;
        private Long userId;
        private String email;
        private String nickname;
        private String profileImage;
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
