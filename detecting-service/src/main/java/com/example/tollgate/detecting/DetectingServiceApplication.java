package com.example.tollgate.detecting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.tollgate")
public class DetectingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DetectingServiceApplication.class, args);
    }

}
