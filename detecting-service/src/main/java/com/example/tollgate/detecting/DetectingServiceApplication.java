package com.example.tollgate.detecting;

import com.example.tollgate.model.HeartBeat;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@SpringBootApplication
@ComponentScan("com.example.tollgate")
public class DetectingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DetectingServiceApplication.class, args);
    }
//
//    @Bean
//    public Consumer<Message<HeartBeat>> heartbeatConsumer() {
//        return message -> LogFactory.getLog(DetectingServiceApplication.class).info("Received " + message.getPayload().getTimestamp());
//    }
        @Bean
    public Consumer<Message<String>> heartbeatConsumer() {
        return message -> LogFactory.getLog(DetectingServiceApplication.class).info("Received " + message.getPayload());
    }
}
