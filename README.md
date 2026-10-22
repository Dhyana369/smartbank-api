# SmartBank — Banking REST API

A Java-based banking application built with Spring Boot REST APIs and a CSV-backed persistence layer. The project demonstrates core banking operations, transaction tracking, validation, exception handling, and duplicate-request protection.

## Tech Stack

- Java
- Spring Boot
- Maven
- REST APIs
- Bean Validation
- JUnit 5
- Mockito
- CSV file storage

## Features

- Create and retrieve bank accounts
- Deposit and withdraw money
- Transfer money between accounts
- Retrieve account transaction history
- Validate amounts and reject insufficient-balance withdrawals
- Prevent transfers to the same account
- Persist accounts and transactions in CSV files
- Reject duplicate transaction IDs
- Use idempotency keys to recognize repeated account-creation requests
- Handle API errors with meaningful HTTP responses

## API Endpoints

Base URL: `http://localhost:8080`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/accounts` | Create an account |
| GET | `/api/accounts/{accountNumber}` | Retrieve an account |
| POST | `/api/accounts/{accountNumber}/deposit` | Deposit money |
| POST | `/api/accounts/{accountNumber}/withdraw` | Withdraw money |
| POST | `/api/accounts/transfer` | Transfer money |
| GET | `/api/accounts/{accountNumber}/transactions` | Retrieve transaction history |

### Create an account

Send a `POST` request to `/api/accounts`.

Header:

`Content-Type: application/json`

`Idempotency-Key: unique-request-key`

Example body:

```json
{
  "customerId": "C100006",
  "initialDeposit": 100
}
```

Use an existing customer ID from your customer data.

### Deposit

`POST /api/accounts/{accountNumber}/deposit`

```json
{
  "amount": 100
}
```

### Withdraw

`POST /api/accounts/{accountNumber}/withdraw`

```json
{
  "amount": 50
}
```

### Transfer

`POST /api/accounts/transfer`

```json
{
  "fromAccountNumber": "SB100006",
  "toAccountNumber": "SB100004",
  "amount": 25
}
```

## Run the Project

1. Install a compatible JDK.
2. Open the project in IntelliJ IDEA.
3. Reload the Maven project.
4. Ensure the original SmartBank library dependency is available in your local Maven repository.
5. Run `SmartbankApiApplication`.
6. Use Postman to send requests to `http://localhost:8080`.

## Run Tests

Run the tests in IntelliJ, or from the project root use:

```bash
./mvnw test
```

## Data Storage

The application uses CSV files from the original SmartBank project's data directory. Keep the data files intact when running the API, and configure the data path appropriately for your environment.

## Future Improvements

- Add stronger atomic handling for account creation and idempotency records
- Add automated integration tests for all endpoints
- Add authentication and authorization
- Introduce a relational database
- Add API documentation using OpenAPI/Swagger

## Disclaimer

This is an educational portfolio project, not a production banking system.
