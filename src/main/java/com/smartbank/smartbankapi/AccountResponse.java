package com.smartbank.smartbankapi;

import service.BankService;

import java.math.BigDecimal;

public class AccountResponse {
    private String message;
    private String customerId;
    private String accountNumber;
    private BigDecimal balance;

    public AccountResponse(String message, String customerId, String accountNumber, BigDecimal balance){
        this.message = message;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getMessage(){
        return message;
    }

    public String getCustomerId(){
        return customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
