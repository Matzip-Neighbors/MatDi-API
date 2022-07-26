package com.mn.matdi.service;

import com.mn.matdi.dto.user.UserSignUpRequestDto;
import com.mn.matdi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public Long userSignup(UserSignUpRequestDto userSignUpRequestDto) {

        return userMapper.insertUser(userSignUpRequestDto);
    }
}

