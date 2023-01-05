package com.c.cclientparent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.c.cclientparent.**.mapper")
public class CClientParentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CClientParentApplication.class, args);
    }

}
