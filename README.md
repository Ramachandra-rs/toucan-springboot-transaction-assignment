# Customer Transaction Service

A Spring Boot REST API for managing customer transactions.

This project was developed as part of the Customer Transactions coding exercise. The implementation focuses on clean Java design, validation, business rules, error handling, automated testing, API testing, and maintainable project structure.

---

## Overview

The service provides four core operations:

1. Create a transaction
2. Retrieve a transaction by Transaction ID
3. Update the status of an existing transaction
4. Retrieve all transactions for a Customer ID

The application uses an embedded H2 database for persistence and exposes the functionality through REST APIs.

---

## Assignment Requirements Covered

This implementation addresses all required areas of the exercise:

- **Validation rules:** Transaction ID, Customer ID, amount, currency, transaction type, and initial status are validated and documented.
- **Status transition rules:** New transactions start as `PENDING`. Only `PENDING → COMPLETED` and `PENDING → FAILED` are permitted.
- **Automated tests:** The project contains 8 automated tests covering successful operations and important validation/error scenarios.
- **Error handling:** Application-specific exceptions and validation failures are handled centrally through `GlobalExceptionHandler` with appropriate HTTP status codes.
- **Complete test suite:** The Maven test suite was executed successfully with 8 tests passing and no failures or errors.
- **AI assistance disclosure:** AI assistance used during development, debugging, testing guidance, and documentation has been disclosed in this README.

---

## Key Features

- RESTful API using Spring Boot
- Layered Controller-Service-Repository architecture
- Transaction persistence using Spring Data JPA
- H2 in-memory database
- Request validation using Jakarta Bean Validation
- Duplicate Transaction ID protection
- Business validation for transaction amounts
- Controlled transaction status transitions
- Centralized exception handling
- Consistent API error responses
- Automated testing using JUnit
- Manual REST API testing using Postman
- API test evidence screenshots

---

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Spring Boot 3.5.5 | Application framework |
| Spring Web | REST API development |
| Spring Data JPA | Data persistence |
| H2 Database | Embedded database |
| Jakarta Validation | Request validation |
| JUnit 5 | Automated testing |
| Mockito / MockMvc | Application testing |
| Postman | Manual REST API testing |
| Maven | Build and dependency management |

---

# API Summary

| Operation | Method | Endpoint | Description | Success |
|---|---|---|---|---|
| Create Transaction | `POST` | `/api/transactions` | Creates a new transaction | `201 Created` |
| Get Transaction | `GET` | `/api/transactions/{transactionId}` | Retrieves a transaction by ID | `200 OK` |
| Update Status | `PATCH` | `/api/transactions/{transactionId}/status` | Changes a transaction's status | `200 OK` |
| Get Customer Transactions | `GET` | `/api/customers/{customerId}/transactions` | Retrieves all transactions for a customer | `200 OK` |

### Error Responses

| HTTP Status | Scenario |
|---|---|
| `400 Bad Request` | Validation failure or invalid status transition |
| `404 Not Found` | Transaction does not exist |
| `409 Conflict` | Transaction ID already exists |

---

# Architecture

The application follows a layered architecture:

```text
                    Client
                      |
                      v
             TransactionController
                      |
                      v
               TransactionService
                      |
                      v
             TransactionRepository
                      |
                      v
                 H2 Database

---text

## Postman API Testing

The REST APIs were manually tested using **Postman** to verify both
successful operations and expected error scenarios.

Postman was used to send HTTP requests to the locally running Spring Boot
application at:

```text
http://localhost:8080
