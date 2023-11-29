package com.example.pharmacysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.pharmacysystem"} )
public class PharmacySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmacySystemApplication.class, args);
    }

}
