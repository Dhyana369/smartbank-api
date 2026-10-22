package com.smartbank.smartbankapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import service.BankService;

import java.nio.file.Path;

@Configuration
public class BankServiceConfig {

    @Bean
    public BankService bankService(
            @Value("${smartbank.data-directory}") String dataDirectory) {
        return new BankService(Path.of(dataDirectory));
    }
}
