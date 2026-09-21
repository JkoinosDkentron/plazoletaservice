package com.juanda.powerup.plazoletaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PlazoletaserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlazoletaserviceApplication.class, args);
    }

}
