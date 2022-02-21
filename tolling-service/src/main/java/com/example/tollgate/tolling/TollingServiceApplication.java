package com.example.tollgate.tolling;

import com.example.tollgate.channel.TollingContextConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.tollgate")
public class TollingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TollingServiceApplication.class, args);
    }

    @Bean
    public TollingContextConsumer tollingTransitionConsumer() {
        return new TollingContextConsumer();
    }
    
}