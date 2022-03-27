package com.mn.matdi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mn.matdi.dto.LoginResponseDto;
import com.mn.matdi.entity.User;
import com.mn.matdi.security.jwt.JwtTokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "BEARER";

    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {
        final UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());

        // Token 생성
        final String token = JwtTokenUtils.generateJwtToken(userDetails);
        response.addHeader(AUTH_HEADER, TOKEN_TYPE + " " + token);

        //UserId, Nickname 내려주기
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        User user = userDetails.getUser();
        LoginResponseDto responseDto = LoginResponseDto.builder()
                .userId(user.getId())
                .username(userDetails.getUsername())
                .nickname(user.getUserNm())
                .build();

        String result = mapper.writeValueAsString(responseDto);
        response.getWriter().write(result);
        System.out.println("LOGIN SUCCESS!");
    }
}
