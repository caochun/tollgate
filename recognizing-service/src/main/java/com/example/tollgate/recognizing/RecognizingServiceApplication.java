package com.example.tollgate.recognizing;

import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@SpringBootApplication
public class RecognizingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecognizingServiceApplication.class, args);
    }


    @Bean
    public Consumer<Message<String>> heartbeatConsumer() {
        return message -> LogFactory.getLog(RecognizingServiceApplication.class).info("Received " + message.getPayload());
    }
}
