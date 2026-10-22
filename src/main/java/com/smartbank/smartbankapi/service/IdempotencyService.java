
package com.smartbank.smartbankapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Service
public class IdempotencyService {

    private final Path file;

    public IdempotencyService(
            @Value("${smartbank.data-directory}") String dataDirectory) {
        this.file = Path.of(dataDirectory, "account_requests.csv");
    }

    public synchronized String findAccountNumber(
            String key, String customerId, BigDecimal initialDeposit) {

        try {
            if (!Files.exists(file)) {
                return null;
            }

            for (String line : Files.readAllLines(file)) {
                String[] parts = line.split(",", -1);

                if (parts.length >= 2 && parts[0].equals(key)) {
                    if (parts.length == 2) {
                        // Supports your existing CSV records.
                        return parts[1];
                    }

                    if (!parts[2].equals(customerId)
                            || new BigDecimal(parts[3])
                            .compareTo(initialDeposit) != 0) {
                        throw new IllegalArgumentException(
                                "Idempotency key reused with different request data");
                    }

                    return parts[1];
                }
            }

            return null;

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to read account requests", e);
        }
    }

    public synchronized void saveKey(
            String key,
            String accountNumber,
            String customerId,
            BigDecimal initialDeposit) {

        if (findAccountNumber(key, customerId, initialDeposit) != null) {
            throw new IllegalArgumentException(
                    "Idempotency key already exists");
        }

        try {
            Files.writeString(
                    file,
                    key + "," + accountNumber + ","
                            + customerId + ","
                            + initialDeposit.toPlainString()
                            + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to save account request", e);
        }
    }
}
