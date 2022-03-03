package com.mn.matdi.controller;

import com.mn.matdi.dto.KakaoUser;
import com.mn.matdi.service.KakaoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final KakaoUserService kakaoUserService;

    @GetMapping("/api/user/kakao/callback")
    public ResponseEntity<KakaoUser.Response> kakaoLogin(@RequestParam String code) {

        return ResponseEntity.ok()
                .body(kakaoUserService.kakaoLogin(code));
    }
}
