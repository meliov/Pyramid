package com.example.demo.bank.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.concurrent.locks.ReentrantLock;

@Configuration

public class Config {
    @Bean
    public ReentrantLock getReentrantLock(){
        return new ReentrantLock();
    }
}
