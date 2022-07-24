package com.mn.matdi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpResponseDto {
    private String email;
    private String userNm;
    private String userProfPhotoPath;
}
