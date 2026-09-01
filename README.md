# Customer Transaction Service

A Spring Boot REST API for managing customer transactions.

This project was developed as part of the Customer Transactions coding exercise. The implementation focuses on clean Java design, validation, business rules, error handling, automated testing, and maintainable project structure.

---

## Overview

The service provides four core operations:

1. Create a transaction
2. Retrieve a transaction by Transaction ID
3. Update the status of an existing transaction
4. Retrieve all transactions for a Customer ID

The application uses an embedded H2 database for persistence and exposes the functionality through REST APIs.

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
- Automated tests using JUnit
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

## Architecture

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
