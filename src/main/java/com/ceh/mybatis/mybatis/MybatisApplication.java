package com.ceh.mybatis.mybatis;

import com.ceh.mybatis.mybatis.config.CuxMybatisConfiguration;
import com.ceh.mybatis.mybatis.config.MybatisConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({CuxMybatisConfiguration.class})
public class MybatisApplication {
	public static void main(String[] args) {
		SpringApplication.run(MybatisApplication.class, args);
	}
}
