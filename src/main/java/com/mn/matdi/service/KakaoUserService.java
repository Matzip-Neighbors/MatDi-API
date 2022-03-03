package com.mn.matdi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mn.matdi.dto.KakaoUser;
import com.mn.matdi.entity.User;
import com.mn.matdi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Value("${kakao.client-id}")
    private String clientId;
    private String accessToken;

    public KakaoUser.Response kakaoLogin(String code) {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);

        // 2. "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        KakaoUser.Response snsUserInfoDto = getKakaoUserInfo(accessToken);

        // 3. "카카오 사용자 정보"로 필요시 회원가입  및 이미 같은 이메일이 있으면 기존회원으로 로그인
        User kakaoUser = registerKakaoOrUpdateKakao(snsUserInfoDto);

        // 4. 강제 로그인 처리
        final String AUTH_HEADER = "Authorization";
        final String TOKEN_TYPE = "BEARER";

        String jwt_token = forceLogin(kakaoUser); // 로그인처리 후 토큰 받아오기
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_HEADER, TOKEN_TYPE + " " + jwt_token);
        KakaoUser.Response kakaoUserResponseDto = KakaoUser.Response.builder()
                .token(TOKEN_TYPE + " " + jwt_token)
                .userId(kakaoUser.getId())
                .nickname(kakaoUser.getUser_nm())
                .profileImage(kakaoUser.getUser_prof_photo_path())
                .build();
        System.out.println("kakao user's token : " + TOKEN_TYPE + " " + jwt_token);
        System.out.println("LOGIN SUCCESS!");
        return null;
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", "http://localhost:3000/redirect/kakao");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private KakaoUser.Response getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        this.accessToken = accessToken;
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String profileImage = jsonNode.get("properties").get("profile_image").asText();

        return KakaoUser.Response.builder()
                .userId(id)
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }


    private User registerKakaoOrUpdateKakao(KakaoUser.Response kakaoUserInfoDto) {
        Optional<User> sameUser = userMapper.findByUserEmail(kakaoUserInfoDto.getEmail());

        if (sameUser.isPresent()) {
            return updateKakaoUser(sameUser, kakaoUserInfoDto);
        } else {
            return registerKakaoUserIfNeeded(kakaoUserInfoDto);
        }
    }


    private User registerKakaoUserIfNeeded(KakaoUser.Response kakaoUserInfoDto) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        String kakaoEmail = kakaoUserInfoDto.getEmail();
        Optional<User> kakaoUser = userMapper.findByUserEmail(kakaoEmail);

        if (!kakaoUser.isPresent()) {
            // 회원가입
            // username: random UUID
            String username = "KAKAO" + UUID.randomUUID();

            // username: kakao nickname
            String nickname = kakaoUserInfoDto.getNickname();
            Optional<User> user = userRepository.findByNickname(nickname);
            if(user.isPresent()) {
                String dbUserNickname = user.get().getNickname();

                int beginIndex= nickname.length();
                String nicknameIndex = dbUserNickname.substring(beginIndex, dbUserNickname.length());

                if (!nicknameIndex.isEmpty()) {
                    int newIndex = Integer.parseInt(nicknameIndex) + 1;
                    nickname = nickname + newIndex;
                } else {
                    nickname = dbUserNickname + 1;
                }
            }


            // profileImage: kakao profile image
            String profileImage = kakaoUserInfoDto.getProfileImage();

            // password: random UUID
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            kakaoUser = User.builder()
                    .username(username)
                    .password(encodedPassword)
                    .nickname(nickname)
                    .kakaoId(kakaoId)
                    .profileImage(profileImage)
                    .build();
            userMapper.save(kakaoUser);
        }

        return kakaoUser.get();
    }
}
