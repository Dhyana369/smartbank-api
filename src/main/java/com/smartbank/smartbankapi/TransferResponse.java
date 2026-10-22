
package com.smartbank.smartbankapi;

import java.math.BigDecimal;

public class TransferResponse {

    private String message;
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
    private BigDecimal fromBalance;
    private BigDecimal toBalance;

    public TransferResponse(
            String message,
            String fromAccountNumber,
            String toAccountNumber,
            BigDecimal amount,
            BigDecimal fromBalance,
            BigDecimal toBalance) {

        this.message = message;
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
        this.fromBalance = fromBalance;
        this.toBalance = toBalance;
    }

    public String getMessage() { return message; }
    public String getFromAccountNumber() { return fromAccountNumber; }
    public String getToAccountNumber() { return toAccountNumber; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getFromBalance() { return fromBalance; }
    public BigDecimal getToBalance() { return toBalance; }
}
