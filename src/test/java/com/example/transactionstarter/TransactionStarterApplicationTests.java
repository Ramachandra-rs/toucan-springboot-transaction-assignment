package com.example.transactionstarter;

import com.example.transactionstarter.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionStarterApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();
    }

    @Test
    void createTransaction_success() throws Exception {

        String request = """
                {
                    "transactionId": "TXN1001",
                    "customerId": "CUS1001",
                    "amount": 5000,
                    "currency": "INR",
                    "transactionType": "PAYMENT"
                }
                """;

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId", is("TXN1001")))
                .andExpect(jsonPath("$.customerId", is("CUS1001")))
                .andExpect(jsonPath("$.status", is("PENDING")));
    }

    @Test
    void createTransaction_validationFailure() throws Exception {

        String request = """
                {
                    "transactionId": "",
                    "customerId": "CUS1001",
                    "amount": -500,
                    "currency": "INR",
                    "transactionType": "PAYMENT"
                }
                """;

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTransaction_duplicateId() throws Exception {

        String request = """
                {
                    "transactionId": "TXN1001",
                    "customerId": "CUS1001",
                    "amount": 5000,
                    "currency": "INR",
                    "transactionType": "PAYMENT"
                }
                """;

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isConflict());
    }

    @Test
    void getTransaction_notFound() throws Exception {

        mockMvc.perform(get("/api/transactions/TXN9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTransaction_success() throws Exception {

        String request = """
                {
                    "transactionId": "TXN1001",
                    "customerId": "CUS1001",
                    "amount": 5000,
                    "currency": "INR",
                    "transactionType": "PAYMENT"
                }
                """;

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/transactions/TXN1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId", is("TXN1001")))
                .andExpect(jsonPath("$.status", is("PENDING")));
    }

    @Test
    void updateStatus_pendingToCompleted() throws Exception {

        String createRequest = """
                {
                    "transactionId": "TXN2001",
                    "customerId": "CUS1001",
                    "amount": 2500,
                    "currency": "INR",
                    "transactionType": "PAYMENT"
                }
                """;

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequest))
                .andExpect(status().isCreated());

        String statusRequest = """
                {
                    "status": "COMPLETED"
                }
                """;

        mockMvc.perform(patch("/api/transactions/TXN2001/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(statusRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("COMPLETED")));
    }

    @Test
    void getCustomerTransactions_success() throws Exception {

        String firstRequest = """
                {
                    "transactionId": "TXN3001",
                    "customerId": "CUS1001",
                    "amount": 1000,
                    "currency": "INR",
                    "transactionType": "PAYMENT"
                }
                """;

        String secondRequest = """
                {
                    "transactionId": "TXN3002",
                    "customerId": "CUS1001",
                    "amount": 2000,
                    "currency": "USD",
                    "transactionType": "TRANSFER"
                }
                """;

        String otherCustomerRequest = """
                {
                    "transactionId": "TXN3003",
                    "customerId": "CUS1002",
                    "amount": 3000,
                    "currency": "EUR",
                    "transactionType": "REFUND"
                }
                """;

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(firstRequest))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(secondRequest))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(otherCustomerRequest))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/customers/CUS1001/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
        @Test
        void createTransaction_amountExceedsMaximum () throws Exception {

            String request = """
                    {
                        "transactionId": "TXN4001",
                        "customerId": "CUS1001",
                        "amount": 1000001,
                        "currency": "INR",
                        "transactionType": "PAYMENT"
                    }
                    """;

            mockMvc.perform(post("/api/transactions")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isBadRequest());
        }
    }
