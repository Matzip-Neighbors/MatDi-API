package com.mn.matdi.security;

import com.mn.matdi.config.MvcConfig;
import com.mn.matdi.security.filter.JwtAuthFilter;
import com.mn.matdi.security.jwt.HeaderTokenExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하도록함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MvcConfig mvcConfig;
    private final HeaderTokenExtractor headerTokenExtractor;

    // BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 객체
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationProviders를 쉽게 추가 할 수 있도록하여 인증 메커니즘을 설정하는 데 사용
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    // js, css, image 설정은 보안 설정의 영향 밖에 있도록 만들어주는 설정.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2-console/**")
                .antMatchers("/v2/api-docs", "/swagger-resources/**", "**/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**");

    }

     /*HttpSecurity 를 사용하면 선택 일치를 기반으로 리소스 수준에서 웹 기반 보안을 구성
     WebSecurity에 접근 허용 설정을 해버리면 이 설정이 적용되지 않는다.*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().disable();
        // Cors 설정

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }



    @Bean
    public FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter formLoginFilter = new FormLoginFilter(authenticationManager());
        formLoginFilter.setFilterProcessesUrl("/user/login");
        //formLoginFilter.setAuthenticationSuccessHandler(formLoginSuccessHandler()); // 인증 성공시 호출할 핸들러 지정
        formLoginFilter.afterPropertiesSet();
        return formLoginFilter;
    }

    private JwtAuthFilter jwtFilter() throws Exception {
        List<String> skipPathList = new ArrayList<>();

        skipPathList.add("GET,/oauth/callback/kakao");

 /*       // h2-console 허용.
        skipPathList.add("GET,/h2-console/**");
        skipPathList.add("POST,/h2-console/**");*/

        // 회원 관리 API 허용
        skipPathList.add("GET,/user/**");
        skipPathList.add("POST,/user/signup");

        // Image View 허용
        skipPathList.add("GET,/images/**");
        skipPathList.add("GET,/"); // 임시...

        // Swagger
        skipPathList.add("GET, /swagger-ui.html");
        skipPathList.add("GET, /swagger/**");
        skipPathList.add("GET, /swagger-resources/**");
        skipPathList.add("GET, /webjars/**");
        skipPathList.add("GET, /v2/api-docs");

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


}
