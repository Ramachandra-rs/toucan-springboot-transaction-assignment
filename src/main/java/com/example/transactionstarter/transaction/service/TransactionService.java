package com.example.transactionstarter.transaction.service;
import com.example.transactionstarter.transaction.dto.CreateTransactionRequest;
import com.example.transactionstarter.transaction.dto.UpdateStatusRequest;
import com.example.transactionstarter.transaction.entity.Transaction;
import com.example.transactionstarter.transaction.enums.TransactionStatus;
import com.example.transactionstarter.transaction.exception.DuplicateTransactionException;
import com.example.transactionstarter.transaction.exception.InvalidStatusTransitionException;
import com.example.transactionstarter.transaction.exception.InvalidTransactionException;
import com.example.transactionstarter.transaction.exception.TransactionNotFoundException;
import com.example.transactionstarter.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    private static final BigDecimal MAX_TRANSACTION_AMOUNT =
            new BigDecimal("1000000");

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(CreateTransactionRequest request) {

        if (transactionRepository.existsById(request.getTransactionId())) {
            throw new DuplicateTransactionException(
                    "Transaction ID already exists: "
                            + request.getTransactionId()
            );
        }

        if (request.getAmount().compareTo(MAX_TRANSACTION_AMOUNT) > 0) {
            throw new InvalidTransactionException(
                    "Transaction amount cannot exceed 1000000"
            );
        }

        Transaction transaction = new Transaction();

        transaction.setTransactionId(request.getTransactionId());
        transaction.setCustomerId(request.getCustomerId());
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(request.getCurrency());
        transaction.setTransactionType(request.getTransactionType());
        transaction.setStatus(TransactionStatus.PENDING);

        return transactionRepository.save(transaction);
    }

    public Transaction getTransaction(String transactionId) {

        return transactionRepository.findById(transactionId)
                .orElseThrow(() ->
                        new TransactionNotFoundException(
                                "Transaction not found: " + transactionId
                        ));
    }

    public Transaction updateStatus(
            String transactionId,
            UpdateStatusRequest request) {

        Transaction transaction = getTransaction(transactionId);

        TransactionStatus currentStatus = transaction.getStatus();
        TransactionStatus newStatus = request.getStatus();

        if (currentStatus != TransactionStatus.PENDING) {
            throw new InvalidStatusTransitionException(
                    "Status cannot be changed after transaction is finalized"
            );
        }

        if (newStatus != TransactionStatus.COMPLETED
                && newStatus != TransactionStatus.FAILED) {

            throw new InvalidStatusTransitionException(
                    "A pending transaction can only become COMPLETED or FAILED"
            );
        }

        transaction.setStatus(newStatus);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getCustomerTransactions(String customerId) {

        return transactionRepository.findByCustomerId(customerId);
    }
}