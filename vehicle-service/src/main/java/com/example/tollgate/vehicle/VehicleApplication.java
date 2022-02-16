package com.example.tollgate.vehicle;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.function.Supplier;

@SpringBootApplication
public class VehicleApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(VehicleApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }

    @Bean
    public Supplier<String> heartbeat() {
        return () -> "heartbeat";
    }

}
