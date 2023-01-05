package com.c.cclientparent.oss.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * oss配置类
 *
 * @author Nineteen
 * @since 1.0.0 [2023/1/2 18:25]
 */
@Configuration
@EnableConfigurationProperties(OssProperties.class)
public class OssConfig {

    @Bean
    public OSS getOssClient(OssProperties ossProperties){
        return new OSSClientBuilder().build(
                ossProperties.getEndpoint(),
                ossProperties.getKeyId(),
                ossProperties.getKeySecret());
    }


}
