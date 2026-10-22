package com.smartbank.smartbankapi.controller;

import com.smartbank.smartbankapi.*;
import com.smartbank.smartbankapi.service.AccountService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public AccountResponse createAccount(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody CreateAccountRequest request) {

        return accountService.createAccount(request, idempotencyKey);
    }


    @PostMapping("/{accountNumber}/deposit")
    public AccountResponse deposit(
            @PathVariable String accountNumber,
            @Valid @RequestBody DepositRequest request) {

        return accountService.deposit(accountNumber, request);
    }

    @PostMapping("/{accountNumber}/withdraw")
    public AccountResponse withdraw(
            @PathVariable String accountNumber,
            @Valid @RequestBody WithdrawRequest request) {

        return accountService.withdraw(accountNumber, request);
    }

    @PostMapping("/transfer")
    public TransferResponse transfer(
            @Valid @RequestBody TransferRequest request) {
        return accountService.transfer(request);
    }

    @GetMapping("/{accountNumber}")
    public AccountResponse getAccount(
            @PathVariable String accountNumber) {
        return accountService.getAccount(accountNumber);
    }

    @GetMapping("/{accountNumber}/transactions")
    public List<TransactionResponse> getTransactionsForAccount(
            @PathVariable String accountNumber) {
        return accountService.getTransactionsForAccount(accountNumber);
    }

}
