package com.mn.matdi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {

    private static final String API_NAME = "맛디";
    private static final String API_DESCRIPTION = "맛디 입니다.";
    private static final String API_VERSION = "1.0.0";
    private static final String API_TERMS_OF_SERVICE_URL = "https://github.com/Matzip-Neighbors";


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                // 스웨거가 RestController를 전부 스캔을 한다.
                // basePackage => 어디를 범위로 스캔을 할 것인지 작성
                .paths(PathSelectors.any())
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
                .title(API_NAME)
                .description(API_DESCRIPTION)
                .version(API_VERSION)
                .termsOfServiceUrl(API_TERMS_OF_SERVICE_URL)
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