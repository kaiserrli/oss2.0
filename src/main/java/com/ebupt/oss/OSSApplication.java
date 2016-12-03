package com.ebupt.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class OSSApplication {
	public static void main(String[] args) {
		SpringApplication.run(OSSApplication.class, args);
	}
}
