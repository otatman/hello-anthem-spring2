package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloAnthemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloAnthemApplication.class, args);
		Greeter greeter = new Greeter();
		System.out.println(greeter.sayHelloAnthem());
	}

}
