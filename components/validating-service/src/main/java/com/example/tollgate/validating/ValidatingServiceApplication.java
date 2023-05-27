package com.example.tollgate.validating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.tollgate")
public class ValidatingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValidatingServiceApplication.class, args);
    }

}