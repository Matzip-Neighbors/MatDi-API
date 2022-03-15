package com.mn.matdi.config;

import com.mn.matdi.controller.KakaoUserController;
import com.mn.matdi.service.KakaoUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.RequestHandlerSelectors.withMethodAnnotation;


@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(AuthenticationPrincipal.class, KakaoUserController.class, KakaoUserService.class) //제외할 파라미터
                .select()
                    .apis(withMethodAnnotation(ApiOperation.class))
                .build()
                .apiInfo(apiInfo());

//                 여기부터 jwt를 위한 설정
//                .forCodeGeneration(true)
//                .ignoredParameterTypes(java.sql.Date.class)
//                .genericModelSubstitutes(ResponseEntity.class)
//                .securityContexts(Lists.newArrayList(securityContext()))
//                .securitySchemes(Lists.newArrayList(authorizationKey()))
//                .useDefaultResponseMessages(false);
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("맛디")
                .description("맛디 입니다.")
                .version("1.0.0")
                .termsOfServiceUrl("https://github.com/Matzip-Neighbors")
                .build();
    }

//    private ApiKey authorizationKey() {
//        return new ApiKey("JWT_TOKEN", "Authorization", "header");
//    }

//    private springfox.documentation.spi.service.contexts.SecurityContext securityContext() {
//
//        return springfox.documentation.spi.service.contexts.SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .forPaths(PathSelectors.any())
//                .build();
//    }

//    private List<SecurityReference> defaultAuth() {
//
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//
//        return Lists.newArrayList(new SecurityReference("JWT_TOKEN", authorizationScopes),
//                new SecurityReference("HTTP_REQUEST", authorizationScopes));
//    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
    }

    // 완료가 되었으면 오른쪽 URL 로 접속 => http://localhost:8080/swagger-ui.html
    // Swagger 3.0.0 이상 버전을 사용할 경우/swagger-ui 로 접속
}