package com.mn.matdi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class EmailRequestDto {

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class request {
                private Long id;
                private String email;
                private String vrfNo; //인증번호
                private String vrfTpCd; //이메일 0010 ,문자 0020
                private String vrfStatCd; //미인증 0010, 인증 0020, 인증실패 0030
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class response {
                private String email;
                private String vrf_no;
                private String vrf_tp_cd;
                private String vrf_stat_cd;
                private LocalDateTime expr_dtm;
        }
}
