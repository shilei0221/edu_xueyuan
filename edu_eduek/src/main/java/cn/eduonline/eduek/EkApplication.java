package cn.eduonline.eduek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Alei
 * @create 2019-07-26 20:34
 */
@SpringBootApplication
@EnableEurekaServer
public class EkApplication {

    public static void main(String[] args) {
        SpringApplication.run(EkApplication.class,args);
    }
}
