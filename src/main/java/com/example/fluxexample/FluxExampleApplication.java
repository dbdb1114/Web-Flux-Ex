package com.example.fluxexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.fluxexample.repository"})
public class FluxExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(FluxExampleApplication.class, args);
    }

}