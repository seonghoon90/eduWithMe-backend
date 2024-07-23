package com.sparta.eduwithme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EduWithMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduWithMeApplication.class, args);
    }

}
