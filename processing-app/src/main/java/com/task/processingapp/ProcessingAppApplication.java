package com.task.processingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class ProcessingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessingAppApplication.class, args);
	}

}
