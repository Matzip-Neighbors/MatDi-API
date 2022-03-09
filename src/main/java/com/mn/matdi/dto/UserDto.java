package com.mn.matdi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Integer user_id;
        private String email;
        private String user_pwd;
        private String user_nm;
        private String user_prof_photo_path;
        private String user_reg_tp_cd;
        private String user_stat_cd;
        private String admin_yn;
        private Timestamp reg_dtm;
        private Timestamp last_login_dtm;
    }
}
