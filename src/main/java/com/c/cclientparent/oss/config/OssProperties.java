package com.c.cclientparent.oss.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * oss配置类
 *
 * @author Nineteen
 * @since 1.0.0 [2023/1/2 18:23]
 */
@Component
@Data
@ConfigurationProperties(prefix = "aliyun.oss.file")
public class OssProperties {

    /**
     * 地域节点
     */
    @Value("{endpoint}")
    private String endpoint;


    /**
     * key
     */
    @Value("{keyid}")
    private String keyId;

    /**
     * secret
     */
    @Value("{keysecret}")
    private String keySecret;


    /**
     * 桶名称
     */
    @Value("{bucketname}")
    private String bucketName;

}
