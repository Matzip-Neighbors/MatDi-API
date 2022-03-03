package com.mn.matdi.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {
    private Long id;
    private String email;
    private String user_pwd;
    private String user_nm;
    private String user_prof_photo_path;
    private String user_reg_tp_cd;
    private String user_stat_cd;
    private char admin_yn;
    private LocalDateTime reg_dtm;
    private LocalDateTime last_login_dtm;
}
