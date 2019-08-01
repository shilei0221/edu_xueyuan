package cn.eduonline.edustatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringBootConfiguration;

/**
 * @author Alei
 * @create 2019-07-27 18:37
 */
@SpringBootConfiguration
@MapperScan("cn.eduonline.edustatis.mapper")
public class StatisConfig {
}
