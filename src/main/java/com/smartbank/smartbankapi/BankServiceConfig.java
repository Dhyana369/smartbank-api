package com.smartbank.smartbankapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import service.BankService;

import java.nio.file.Path;

@Configuration
public class BankServiceConfig {

    @Bean
    public BankService bankService() {
        return new BankService(Path.of("/Users/dhyana/Documents/Project/SmartBank/data"));
    }
}
