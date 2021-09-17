package com.challenge.labs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChallengeLabsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLabsApplication.class, args);
	}

}
