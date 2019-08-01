package cn.eduonline.educenter.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Alei
 * @create 2019-07-26 23:58
 */
@SpringBootConfiguration
@EnableTransactionManagement
@MapperScan("cn.eduonline.educenter.mapper")
public class CenterConfig {
}
