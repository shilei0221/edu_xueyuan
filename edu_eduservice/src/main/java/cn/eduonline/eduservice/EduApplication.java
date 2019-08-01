package cn.eduonline.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Alei
 * @create 2019-07-09 22:30
 */
@ComponentScan(basePackages = {"cn.eduonline.eduservice","cn.eduonline.common"})
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}

