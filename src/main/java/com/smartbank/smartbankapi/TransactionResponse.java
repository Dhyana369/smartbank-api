package com.smartbank.smartbankapi;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {

    private String transactionId;
    private LocalDateTime dateTime;
    private BigDecimal amount;
    private String type;
    private String fromAccount;
    private String toAccount;

    public TransactionResponse(
            String transactionId,
            LocalDateTime dateTime,
            BigDecimal amount,
            String type,
            String fromAccount,
            String toAccount) {

        this.transactionId = transactionId;
        this.dateTime = dateTime;
        this.amount = amount;
        this.type = type;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public String getTransactionId() { return transactionId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public BigDecimal getAmount() { return amount; }
    public String getType() { return type; }
    public String getFromAccount() { return fromAccount; }
    public String getToAccount() { return toAccount; }
}