# Customer Transactions API

A Java Spring Boot REST API for managing customer transactions.

The application supports creating transactions, retrieving transactions,
updating transaction status, and retrieving all transactions for a customer.

## Technology Stack

- Java 17
- Spring Boot 3.5.5
- Spring Web
- Spring Data JPA
- H2 Database
- Bean Validation
- Maven
- JUnit 5
- MockMvc

## Project Structure

```text
src/main/java/com/example/transactionstarter
│
├── TransactionStarterApplication.java
│
└── transaction
    ├── controller
    │   └── TransactionController.java
    │
    ├── dto
    │   ├── CreateTransactionRequest.java
    │   └── UpdateStatusRequest.java
    │
    ├── entity
    │   └── Transaction.java
    │
    ├── enums
    │   ├── Currency.java
    │   ├── TransactionStatus.java
    │   └── TransactionType.java
    │
    ├── exception
    │   ├── ApiError.java
    │   ├── DuplicateTransactionException.java
    │   ├── InvalidStatusTransitionException.java
    │   ├── InvalidTransactionException.java
    │   ├── TransactionNotFoundException.java
    │   └── GlobalExceptionHandler.java
    │
    ├── repository
    │   └── TransactionRepository.java
    │
    └── service
        └── TransactionService.java