package com.example.tollgate.vehicle;

import com.example.tollgate.model.HeartBeat;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import java.util.function.Supplier;

@SpringBootApplication
public class VehicleServiceApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(VehicleServiceApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }

    @Bean
    public Supplier<Message<?>> heartbeat() {
        return () -> {
            LogFactory.getLog(VehicleServiceApplication.class).info("Sending heartbeat");
            return new HeartBeat("billing").toMessage();
        };
    }
}
