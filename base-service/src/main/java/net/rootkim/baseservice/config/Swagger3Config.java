package net.rootkim.baseservice.config;

import net.rootkim.core.utils.HttpHeaderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

/**
 * swagger 配置类
 * @author RootKim[rootkim.net]
 * @since 2024/3/11
 */
@Configuration
@EnableOpenApi
public class Swagger3Config {
    /**
     * 用于读取配置文件 application.properties 中 swagger 属性是否开启
     */
    @Value("${swagger.enabled}")
    private Boolean swaggerEnabled;

    @Bean
    public Docket api() {
        //使用3.0会报错
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerEnabled)
                .ignoredParameterTypes(RequestAttribute.class)
                .ignoredParameterTypes(RequestHeader.class)
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.rootkim.baseservice.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(Collections.singletonList(
                        new RequestParameterBuilder()
                                .name(HttpHeaderUtil.TOKEN_KEY) // 自定义header名称
                                .description("身份令牌") // header描述
                                .in(ParameterType.HEADER)
                                .required(false) // 是否必须
                                .build()
                ));
    }

    //配置token
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey(HttpHeaderUtil.USER_ID_KEY, HttpHeaderUtil.USER_ID_KEY, "header");
    }
}
