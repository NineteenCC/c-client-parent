package com.c.cclientparent.fanyigou.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 翻译狗appId配置类
 *
 * @author Nineteen
 * @since 1.0.0 [2022/12/27 19:00]
 */
@ConfigurationProperties(prefix = "fanyigou")
@Component
@Data
public class AppIdProperties {

    @Value("appid")
    private String appId;

    @Value("secret")
    private String secret;

}
