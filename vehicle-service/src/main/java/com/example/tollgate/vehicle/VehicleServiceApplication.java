package com.example.tollgate.vehicle;

import com.example.tollgate.channel.VehicleContextConsumer;
import com.example.tollgate.model.HeartBeat;
import com.example.tollgate.model.TollgateMessageBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.Message;

import java.util.function.Supplier;

@SpringBootApplication
@ComponentScan("com.example.tollgate")
public class VehicleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicleServiceApplication.class, args);
    }


    @Bean
    public VehicleContextConsumer vehicleTransitionConsumer() {
        return new VehicleContextConsumer();
    }
    
}
