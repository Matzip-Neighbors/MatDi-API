package com.mn.matdi.controller;

import com.mn.matdi.dto.UserDto;
import com.mn.matdi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;

    public Long userSignup(UserDto.Request request) throws Exception {
        return userMapper.insertUser(request);
    }
}
