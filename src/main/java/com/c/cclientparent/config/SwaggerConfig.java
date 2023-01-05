package com.c.cclientparent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Nineteen
 * @since 1.0.0 [2023/1/3 10:08]
 */
//@Configuration
//@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //apiInfo()用来创建该Api的基本信息（这些基本信息会展现在文档页面中）
                .apiInfo(apiInfo())
                //select()函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现
                .select()
                //该包下的controller类生成api文档(除了被@ApiIgnore指定的请求)
                //.apis(RequestHandlerSelectors.basePackage())
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Restful API")
                .description("Funnee's swagger")
                .termsOfServiceUrl("http://localhost:8080")
                .version("1.0")
                .build();
    }
}
