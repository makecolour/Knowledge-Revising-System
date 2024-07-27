package com.krs.knowledgerevisingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KnowledgeRevisingSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(KnowledgeRevisingSystemApplication.class, args);
    }
}
