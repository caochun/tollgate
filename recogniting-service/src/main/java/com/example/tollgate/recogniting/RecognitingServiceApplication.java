package com.example.tollgate.recogniting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class RecognitingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecognitingServiceApplication.class, args);
    }


    @GetMapping("/")
    public String get(){
        return "";
    }

}
