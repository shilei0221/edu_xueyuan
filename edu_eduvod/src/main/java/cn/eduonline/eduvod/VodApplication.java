package cn.eduonline.eduvod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Alei
 * @create 2019-07-25 20:36
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages={"cn.eduonline.eduvod","cn.eduonline.common"})
@EnableEurekaClient
public class VodApplication {

    public static void main(String[] args) {

        SpringApplication.run(VodApplication.class,args);

    }
}
