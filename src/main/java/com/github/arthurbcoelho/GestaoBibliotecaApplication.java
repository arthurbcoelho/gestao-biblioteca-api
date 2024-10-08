package com.github.arthurbcoelho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class GestaoBibliotecaApplication {
    public static void main(String[] args) {
        SpringApplication.run(GestaoBibliotecaApplication.class, args);
    }
}