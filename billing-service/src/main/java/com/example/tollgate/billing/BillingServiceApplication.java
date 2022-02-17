package com.example.tollgate.billing;

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
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    public Consumer<Message<HeartBeat>> heartbeatConsumer() {
        return message -> LogFactory.getLog(BillingServiceApplication.class).info("Received " + message.getPayload().getTimestamp());
    }
}