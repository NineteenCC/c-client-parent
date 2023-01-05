package com.c.cclientparent.fanyigou.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 翻译狗配置类
 * @author Nineteen
 * @since 1.0.0 [2023/1/2 18:26]
 */
@Configuration
@EnableConfigurationProperties({AppIdProperties.class})
public class FanYiGouConfig {
}
