package com.mn.matdi.security;


import com.mn.matdi.security.filter.FormLoginFilter;
import com.mn.matdi.security.filter.JwtAuthFilter;
import com.mn.matdi.security.filter.JwtTokenProvider;
import com.mn.matdi.security.jwt.HeaderTokenExtractor;
import com.mn.matdi.security.provider.FormLoginAuthProvider;
import com.mn.matdi.security.provider.JWTAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthProvider jwtAuthProvider;
    private final JwtTokenProvider jwtTokenProvider;
    private final HeaderTokenExtractor headerTokenExtractor;

    // BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 객체
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationProviders를 쉽게 추가 할 수 있도록하여 인증 메커니즘을 설정하는 데 사용
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // CustomAuthenticationProvider()를 호출하기 위해서 Overriding
        auth
                .authenticationProvider(formLoginAuthProvider())
                .authenticationProvider(jwtAuthProvider);
    }

    // js, css, image 설정은 보안 설정의 영향 밖에 있도록 만들어주는 설정.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2-console/**");

    }

    /*HttpSecurity 를 사용하면 선택 일치를 기반으로 리소스 수준에서 웹 기반 보안을 구성
    WebSecurity에 접근 허용 설정을 해버리면 이 설정이 적용되지 않는다.*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers();;

        http
                .cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable();

        /* 1.
         * UsernamePasswordAuthenticationFilter 이전에 FormLoginFilter, JwtFilter 를 등록합니다.
         * FormLoginFilter : 로그인 인증을 실시합니다.
         * JwtFilter       : 서버에 접근시 JWT 확인 후 인증을 실시합니다.
         */
        http
                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                // 회원 관리 처리 API 전부를 login 없이 허용
                .antMatchers("/api/user").permitAll()
                // 그 외 어떤 요청이든 '인증'
                .anyRequest()
                .permitAll();

    }

    @Bean
    public FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter formLoginFilter = new FormLoginFilter(authenticationManager());
        formLoginFilter.setFilterProcessesUrl("/api/user");
        formLoginFilter.setAuthenticationSuccessHandler(formLoginSuccessHandler()); // 인증 성공시 호출할 핸들러 지정
        formLoginFilter.afterPropertiesSet();
        return formLoginFilter;
    }

    @Bean
    public FormLoginSuccessHandler formLoginSuccessHandler() {
        return new FormLoginSuccessHandler();
    }

    @Bean
    public FormLoginAuthProvider formLoginAuthProvider() {
        return new FormLoginAuthProvider(encodePassword());
    }

    private JwtAuthFilter jwtFilter() throws Exception {
        List<String> skipPathList = new ArrayList<>();

        // 카카오회원 관리 API 허용
        skipPathList.add("GET,/api/user/kakao/callback");
        skipPathList.add("GET,/user/kakao/callback/{userId}");

        skipPathList.add("GET,/oauth/callback/kakao");


        // 회원 관리 API 허용
        skipPathList.add("POST,/api/user"); // 로그인
        skipPathList.add("POST,/api/user/**"); // 소셜로그인
        skipPathList.add("POST,/api/signup"); // 회원가입
        skipPathList.add("GET,/api/signup/**"); // 중복체크

        // Swagger version2
        skipPathList.add("GET,/v2/api-docs");
        skipPathList.add("GET,/swagger-resources");
        skipPathList.add("GET,/configuration/ui");
        skipPathList.add("GET,/configuration/security");
        skipPathList.add("GET,/swagger-ui.html");
        skipPathList.add("GET,/webjars/**");
        skipPathList.add("GET,/swagger-resources/**");

        // Swagger version3
        skipPathList.add("GET,/v3/api-docs/**");
        skipPathList.add("GET,/swagger-ui/**");

        FilterSkipMatcher matcher = new FilterSkipMatcher(
                skipPathList,
                "/**"
        );

        JwtAuthFilter filter = new JwtAuthFilter(
                matcher,
                headerTokenExtractor
        );
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}