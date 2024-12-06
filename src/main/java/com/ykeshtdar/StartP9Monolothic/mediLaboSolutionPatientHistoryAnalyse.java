package com.ykeshtdar.StartP9Monolothic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.mongo.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;

@SpringBootApplication
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
public class mediLaboSolutionPatientHistoryAnalyse {

	public static void main(String[] args) {

		SpringApplication.run(mediLaboSolutionPatientHistoryAnalyse.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
