package com.mn.matdi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mn.matdi.dto.HeaderDto;
import com.mn.matdi.dto.UserDto;
import com.mn.matdi.service.KakaoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final KakaoUserService kakaoUserService;
    private final UserService userService;

/*    @GetMapping("/oauth/callback/kakao")
    public HeaderDto kakaoLogin(
            @RequestParam String code
    ) throws JsonProcessingException {
        return kakaoUserService.kakaoLogin(code);
    }*/

    @PostMapping("/users/signup")
    public Long userSignup(@RequestBody UserDto.Request request) throws Exception {
        return userService.userSignup(request);
    }

}
