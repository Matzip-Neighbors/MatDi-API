package com.mn.matdi.controller;

import com.mn.matdi.dto.UserDto;
import com.mn.matdi.service.KakaoUserService;
import com.mn.matdi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
