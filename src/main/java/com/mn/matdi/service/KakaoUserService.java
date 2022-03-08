package com.mn.matdi.service;

import com.mn.matdi.dto.HeaderDto;
import com.mn.matdi.mapper.UserMapper;
import com.mn.matdi.security.filter.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KakaoUserService {
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;

}
