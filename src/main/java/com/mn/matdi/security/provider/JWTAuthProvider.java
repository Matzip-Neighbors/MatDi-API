package com.mn.matdi.security.provider;


import com.mn.matdi.entity.User;
import com.mn.matdi.mapper.UserMapper;
import com.mn.matdi.security.jwt.JwtDecoder;
import com.mn.matdi.security.jwt.JwtPreProcessingToken;
import com.mn.matdi.security.userdetail.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthProvider implements AuthenticationProvider {
    private final JwtDecoder jwtDecoder;

    private final UserMapper userMapper;

    // JWT 토큰 유효성 검사
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        log.info("토큰이 넘어오는지={}",token);
        String userEmail = jwtDecoder.decodeUsername(token);

        //  API 사용시마다 매번 User DB 조회 필요
        //  -> 해결을 위해서는 UserDetailsImpl 에 User 객체를 저장하지 않도록 수정
        //  ex) UserDetailsImpl 에 userId, username, role 만 저장
        //      -> JWT 에 userId, username, role 정보를 암호화/복호화하여 사용
        User user = userMapper.findByUserEmail(userEmail).orElseThrow(
                () -> new NullPointerException("can't find " + userEmail)
        );
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtPreProcessingToken.class.isAssignableFrom(authentication);
    }
}