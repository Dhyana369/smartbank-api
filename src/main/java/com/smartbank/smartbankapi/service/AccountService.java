
package com.smartbank.smartbankapi.service;

import com.smartbank.smartbankapi.*;

import model.Account;
import model.Customer;

import org.springframework.stereotype.Service;
import service.BankService;

import java.util.List;

@Service
public class AccountService {
    private final BankService bankService;
    private final IdempotencyService idempotencyService;

    public AccountService(BankService bankService, IdempotencyService idempotencyService) {
        this.bankService = bankService;
        this.idempotencyService = idempotencyService;
    }


    public synchronized AccountResponse createAccount(
            CreateAccountRequest request,
            String idempotencyKey) {

        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new IllegalArgumentException(
                    "Idempotency-Key header is required");
        }

        String existingAccountNumber =
                idempotencyService.findAccountNumber(
                        idempotencyKey,
                        request.getCustomerId(),
                        request.getInitialDeposit()
                );

        if (existingAccountNumber != null) {
            return getAccount(existingAccountNumber);
        }

        Customer customer =
                bankService.findCustomer(request.getCustomerId());

        Account account =
                bankService.createAccount(
                        customer, request.getInitialDeposit());

        idempotencyService.saveKey(
                idempotencyKey,
                account.getAccountNumber(),
                request.getCustomerId(),
                request.getInitialDeposit()
        );

        return new AccountResponse(
                "Account created successfully",
                customer.getCustomerId(),
                account.getAccountNumber(),
                account.getBalance()
        );
    }


    public AccountResponse getAccount(String accountNumber) {
        Account account = bankService.findAccount(accountNumber);
        Customer customer = account.getOwner();

        return new AccountResponse(
                "Account retrieved successfully",
                customer.getCustomerId(),
                account.getAccountNumber(),
                account.getBalance()
        );
    }

    public AccountResponse deposit(String accountNumber, DepositRequest request) {

        bankService.deposit(accountNumber, request.getAmount());

        return getAccount(accountNumber);
    }

    public AccountResponse withdraw(String accountNumber, WithdrawRequest request) {

        bankService.withdraw(accountNumber, request.getAmount());

        return getAccount(accountNumber);
    }

    public TransferResponse transfer(TransferRequest request) {

        bankService.transfer(
                request.getFromAccountNumber(),
                request.getToAccountNumber(),
                request.getAmount()
        );

        AccountResponse fromAccount =
                getAccount(request.getFromAccountNumber());

        AccountResponse toAccount =
                getAccount(request.getToAccountNumber());

        return new TransferResponse(
                "Transfer successful",
                request.getFromAccountNumber(),
                request.getToAccountNumber(),
                request.getAmount(),
                fromAccount.getBalance(),
                toAccount.getBalance()
        );
    }

    public List<TransactionResponse> getTransactionsForAccount(
            String accountNumber) {

        return bankService.getTransactionsForAccount(accountNumber)
                .stream()
                .map(transaction -> new TransactionResponse(
                        transaction.getTransactionId(),
                        transaction.getDateTime(),
                        transaction.getAmount(),
                        transaction.getType().name(),
                        transaction.getFromAccount() == null
                                ? null
                                : transaction.getFromAccount().getAccountNumber(),

                        transaction.getToAccount() == null
                                ? null
                                : transaction.getToAccount().getAccountNumber()
                ))
                .toList();
    }

}
