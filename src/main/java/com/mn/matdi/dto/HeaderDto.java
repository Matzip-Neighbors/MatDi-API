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
    private Long userId;
    private String nickName;
    private String profileImg;
    private String address;


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

