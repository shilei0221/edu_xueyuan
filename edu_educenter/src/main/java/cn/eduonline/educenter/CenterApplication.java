package cn.eduonline.educenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Alei
 * @create 2019-07-26 23:57
 */
@SpringBootApplication
@EnableEurekaClient
public class CenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CenterApplication.class,args);
    }
}
