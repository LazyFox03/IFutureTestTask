package com.foxdatabase.ifuturetesttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class IFutureTestTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(IFutureTestTaskApplication.class, args);
    }

}
