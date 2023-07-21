package com.project.paymybuddy.controller;

import com.project.paymybuddy.TransactionService;
import com.project.paymybuddy.model.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<Transactions> createTransaction(Integer giverId, Integer receiverId,
                                                          Integer amount, String description) {

        Transactions newTransactions = transactionService.createTransactions(giverId, receiverId,
                amount, description);
        return new ResponseEntity<>(newTransactions, HttpStatus.CREATED);

    }
}
