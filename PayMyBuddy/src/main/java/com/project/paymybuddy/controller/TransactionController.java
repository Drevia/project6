package com.project.paymybuddy.controller;

import com.project.paymybuddy.model.AppUser;
import com.project.paymybuddy.model.Transaction;
import com.project.paymybuddy.model.TransactionsDto;
import com.project.paymybuddy.service.TransactionService;
import com.project.paymybuddy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    private final static Logger LOG = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping("/transfer")
    public String createTransaction(TransactionsDto transactionsToCreate) {
        LOG.info("create transaction :{}" ,transactionsToCreate);

        AppUser appUser = userService.getUser();

        transactionsToCreate.setGiverId(appUser.getId());
        Transaction newTransaction = transactionService.createTransactions(transactionsToCreate);
        LOG.info("Transaction effectué avec succès: " + newTransaction);
        return "redirect:/transfer";

    }
}
