package com.example.tollgate.recognizing;

import com.example.tollgate.channel.TollingContextConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.tollgate")
public class RecognizingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecognizingServiceApplication.class, args);
    }

    @Bean
    public TollingContextConsumer tollingStateConsumer() {
        return new TollingContextConsumer();
    }

}
