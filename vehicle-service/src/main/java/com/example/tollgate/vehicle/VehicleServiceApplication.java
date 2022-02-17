package com.example.tollgate.vehicle;

import com.example.tollgate.model.Header;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.Instant;
import java.util.function.Supplier;

@SpringBootApplication
public class VehicleServiceApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(VehicleServiceApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }

    @Bean
    public Supplier<Message<String>> heartbeat() {
        return () -> {
            Message<String> message = MessageBuilder.withPayload(Instant.now().toString()).setHeader(Header.CONTENT_TYPE, Header.HEARTBEAT).build();
            LogFactory.getLog(VehicleServiceApplication.class).info("Sending heartbeat");
            return message;
        };
    }
}
