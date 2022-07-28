package com.mn.matdi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mn.matdi.dto.kakao.KakaoUser;
import com.mn.matdi.service.KakaoUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class KakaoUserController {

    private final KakaoUserService kakaoUserService;

    @Operation(summary = "카카오 로그인")
    @GetMapping("/api/user/kakao/callback")
    public ResponseEntity<KakaoUser.Response> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        return ResponseEntity.ok()
                .body(kakaoUserService.kakaoLogin(code));
    }

}
