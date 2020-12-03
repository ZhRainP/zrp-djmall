package com.dj.p2p;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient // 开启客户端注解
@EnableFeignClients // 开启服务之间调用  采用feign
@EnableTransactionManagement
@MapperScan("com.dj.p2p.mapper")
public class RiskControlApplication {
    public static void main(String[] args) {
        SpringApplication.run(RiskControlApplication.class,args);
    }
}
