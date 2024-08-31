package com.fotio.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigserverApplication {
// docker file cd C:\Doc_pc\CORSI\Microservizi_01\microservice\configserver\configserver\Document
// 	docker-compose -f docker-compose.yml -p microservice up -d
	public static void main(String[] args) {
		SpringApplication.run(ConfigserverApplication.class, args);
	}

}
