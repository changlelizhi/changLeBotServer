package com.changle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan("com.changle.mapper")
public class ChangLeBotServerApplication {

	public static void main(String[] args) {
		System.setProperty("socksProxyHost", "127.0.0.1");
		System.setProperty("socksProxyPort", "7890");
		SpringApplication.run(ChangLeBotServerApplication.class, args);
	}

}
