package com.richard.axon;

import org.axonframework.spring.config.EnableAxon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAxon //--spring configuration
@SpringBootApplication
public class AxonBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(AxonBankApplication.class, args);
    }

}
