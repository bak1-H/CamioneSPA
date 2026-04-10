package com.Camionesspa.Arriendo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ArriendoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArriendoApplication.class, args);
	}

}
