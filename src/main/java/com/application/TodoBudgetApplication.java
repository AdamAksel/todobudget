package com.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TodoBudgetApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoBudgetApplication.class, args);
    }
}
