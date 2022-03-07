package com.mn.matdi.dto;

import lombok.Builder;
import lombok.Data;

public class UserDto {

    @Data
    @Builder
    public static class Request {
        private String email;
        private String user_pwd;
        private String user_nm;
    }
}
