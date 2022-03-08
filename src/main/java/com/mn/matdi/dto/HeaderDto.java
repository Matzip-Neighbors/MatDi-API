package com.mn.matdi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeaderDto {
    private String TOKEN;
    private Long user_id;
    private String user_nm;
    private String email;
    private String user_prof_photo_path;


/*    public static HeaderDto createHeaderDto(String token, User kakaoUser){
        return HeaderDto.builder()
                .TOKEN(token)
                .userId(kakaoUser.getId())
                .nickName(kakaoUser.getNickname())
                .profileImg(kakaoUser.getProfileImg())
                .address(kakaoUser.getAddress())
                .build();
    }*/
}

