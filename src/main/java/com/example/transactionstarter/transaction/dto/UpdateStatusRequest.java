package com.example.transactionstarter.transaction.dto;

import com.example.transactionstarter.transaction.enums.TransactionStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateStatusRequest {

    @NotNull(message = "Status is required")
    private TransactionStatus status;

    public UpdateStatusRequest() {
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}