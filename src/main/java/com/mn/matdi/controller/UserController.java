package com.mn.matdi.controller;

import com.mn.matdi.dto.user.UserSignUpRequestDto;
import com.mn.matdi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "맛디 회원가입")
    @PostMapping("/api/user/signup")
    public Long userSignup(@RequestBody  UserSignUpRequestDto userSignUpRequestDto) {
        return userService.userSignup(userSignUpRequestDto);
    }
}
