package com.bni.test.BNI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BniTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BniTestApplication.class, args);
	}

}
