package cn.eduonline.edustatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Alei
 * @create 2019-07-27 18:36
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class StatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatisApplication.class,args);
    }
}
