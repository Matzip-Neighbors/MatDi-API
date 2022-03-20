package com.mn.matdi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mn.matdi.dto.KakaoUser;
import com.mn.matdi.service.KakaoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class KakaoUserController {

    private final KakaoUserService kakaoUserService;

    @PostMapping("/api/user/kakao/callback")
    public ResponseEntity<KakaoUser.Response> kakaoLogin(@RequestBody KakaoUser.AuthCode code) throws JsonProcessingException {
        return ResponseEntity.ok()
                .body(kakaoUserService.kakaoLogin(code));
    }

}
