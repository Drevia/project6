package com.project.paymybuddy.controller;

import com.project.paymybuddy.service.TransactionService;
import com.project.paymybuddy.model.Transaction;
import com.project.paymybuddy.model.TransactionsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    private final static Logger LOG = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> createTransaction(TransactionsDto transactionsToCreate) {
        LOG.info("create transaction :{}" ,transactionsToCreate);
        Transaction newTransaction = transactionService.createTransactions(transactionsToCreate);
        LOG.info("Transaction effectué avec succès");
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);

    }
}
