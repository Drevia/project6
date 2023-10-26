package com.project.paymybuddy.service;

import com.project.paymybuddy.exception.UserException;
import com.project.paymybuddy.model.AppUser;
import com.project.paymybuddy.model.Transaction;
import com.project.paymybuddy.model.TransactionsDto;
import com.project.paymybuddy.repository.TransactionRepository;
import com.project.paymybuddy.repository.UserRepository;
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
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    private final static Logger LOG = LoggerFactory.getLogger(TransactionService.class);

    /**
     *
     * @param transactionsToCreate
     * @return
     */
    @Transactional
    public Transaction createTransactions(TransactionsDto transactionsToCreate) {
        Transaction transaction = new Transaction();
        transaction.setDate(new Date());
        AppUser giver = findUserById(transactionsToCreate.getGiverId());
        AppUser receiver = findUserById(transactionsToCreate.getReceiverId());
        transaction.setGiverId(giver.getId());
        transaction.setReceiverId(receiver.getId());
        if (transactionsToCreate.getAmount() > 0f) {
            transaction.setAmount(transactionsToCreate.getAmount());
        } else {
            LOG.warn("Transaction amount must be superior to 1€");
            throw new UserException("Transaction amount must be superior to 1€");
        }
        if (transactionsToCreate.getDescription() != null)
        {
            transaction.setDescription(transactionsToCreate.getDescription());
        }

        changeSold(giver, receiver, transactionsToCreate.getAmount());
        return transactionRepository.save(transaction);
    }

    private void changeSold(AppUser giver, AppUser receiver, Float amount) {

        LOG.info("create Transaction by: " + giver.getFirstName() + " for: " + receiver.getFirstName());
        if (giver.getSold() - amount < 0) {
            throw new UserException("Cannot create transaction, the user: " + giver.getId() + " have not enough sold");
        }
        giver.setSold(giver.getSold() - amount);
        receiver.setSold(receiver.getSold() + amount);
        userRepository.save(giver);
        userRepository.save(receiver);
    }

    public AppUser findUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UserException("User with id: " + id + " not found"));
    }
}
