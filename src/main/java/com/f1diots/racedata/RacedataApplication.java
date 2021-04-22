package com.f1diots.racedata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class RacedataApplication {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello New Auto-Deployed World!";
	}

	public static void main(String[] args) {
		SpringApplication.run(RacedataApplication.class, args);
	}

}
