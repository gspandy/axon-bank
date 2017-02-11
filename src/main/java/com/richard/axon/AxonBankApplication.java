package com.richard.axon;

import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.spring.config.EnableAxon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableAxon //--spring configuration
@SpringBootApplication
public class AxonBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(AxonBankApplication.class, args);
    }

}
