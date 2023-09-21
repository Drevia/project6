package com.project.paymybuddy.service;

import com.project.paymybuddy.exception.UserException;
import com.project.paymybuddy.model.AppUser;
import com.project.paymybuddy.model.Transaction;
import com.project.paymybuddy.model.TransactionsDto;
import com.project.paymybuddy.repository.TransactionRepository;
import com.project.paymybuddy.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    private final static Logger LOG = LoggerFactory.getLogger(TransactionService.class);

    @Transactional
    public Transaction createTransactions(TransactionsDto transactionsToCreate) {
        Transaction transaction = new Transaction();
        transaction.setDate(new Date());
        transaction.setGiverId(findUserById(transactionsToCreate.getGiverId()));
        transaction.setReceiverId(findUserById(transactionsToCreate.getReceiverId()));
        if (transactionsToCreate.getAmount() != 0) {
            transaction.setAmount(transactionsToCreate.getAmount());
        } else {
            LOG.warn("Transaction amount must be superior to 1€");
            throw new UserException("Transaction amount must be superior to 1€");
        }
        if (transactionsToCreate.getDescription() != null)
        {
            transaction.setDescription(transactionsToCreate.getDescription());
        }

        changeSold(transactionsToCreate.getGiverId(), transactionsToCreate.getReceiverId(), transactionsToCreate.getAmount());
        return transactionRepository.save(transaction);
    }

    private void changeSold(Integer giver_id, Integer receiver_id, Float amount) {

        AppUser giver = userRepository.findById(giver_id).orElseThrow(() ->
                new UserException("Giver with id: " + giver_id + " not Found"));
        AppUser receiver = userRepository.findById(receiver_id).orElseThrow(() ->
                new UserException("Receiver with id: " + receiver_id + " not Found"));

        LOG.info("create Transaction by: " + giver.getFirstName() + " for: " + receiver.getFirstName());
        giver.setSold(giver.getSold() - amount);
        receiver.setSold(receiver.getSold() + amount);
        userRepository.save(giver);
        userRepository.save(receiver);
    }

    public AppUser findUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(/*rajouter throw d'exception*/);
    }
}
