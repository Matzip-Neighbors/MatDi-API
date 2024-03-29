package com.mn.matdi.dto.userVerification;

import lombok.*;


public class UserVerification {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Info {
        private String email;
        private String vrfNo; //인증번호
        private String vrfTpCd; //이메일 0010 ,문자 0020
        private String vrfStatCd; //미인증 0010, 인증 0020, 인증실패 0030
    }

    @Getter
    public static class Request {
        private String email;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String email;
        private String vrfNo;
        private String vrfTpCd;
        private String vrfStatCd;
    }
}
