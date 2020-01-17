package com.jiujiu.xxgame;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
@MapperScan("com.jiujiu.xxgame.mapper")
public class XXGameServer {

	public static void main(String[] args) {
        SpringApplication.run(XXGameServer.class, args);
	}

}
