package com.mn.matdi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mn.matdi.dto.KakaoUser;
import com.mn.matdi.service.KakaoUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoUserController {

    private final KakaoUserService kakaoUserService;

    @GetMapping("/api/user/kakao/callback")
    public ResponseEntity<KakaoUser.Response> kakaoLogin(@RequestParam String code) throws JsonProcessingException {

        return ResponseEntity.ok()
                .body(kakaoUserService.kakaoLogin(code));
    }
}
