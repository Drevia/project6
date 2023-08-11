package com.project.paymybuddy.controller;

import com.project.paymybuddy.TransactionService;
import com.project.paymybuddy.model.Transaction;
import com.project.paymybuddy.model.TransactionsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionsDto transactionsToCreate) {
        System.out.println("create transaction");
        Transaction newTransaction = transactionService.createTransactions(transactionsToCreate);
        System.out.println("Transaction effectué avec succès");
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);

    }
}
