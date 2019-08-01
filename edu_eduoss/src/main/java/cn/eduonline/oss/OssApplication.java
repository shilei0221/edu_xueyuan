package cn.eduonline.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Alei
 * @create 2019-07-14 12:05
 */
//@SpringBootApplication
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)  //排除数据源
public class OssApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class,args);
    }

}
