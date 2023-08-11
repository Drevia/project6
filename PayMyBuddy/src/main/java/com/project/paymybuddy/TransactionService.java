package com.project.paymybuddy;

import com.project.paymybuddy.model.Transaction;
import com.project.paymybuddy.model.TransactionsDto;
import com.project.paymybuddy.model.User;
import com.project.paymybuddy.repository.TransactionRepository;
import com.project.paymybuddy.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public Transaction createTransactions(TransactionsDto transactionsToCreate) {
        Transaction transaction = new Transaction();
        transaction.setDate(new Date());
        transaction.setGiverId(findUserById(transactionsToCreate.getGiverId()));
        transaction.setReceiverId(findUserById(transactionsToCreate.getReceiverId()));
        transaction.setAmount(transactionsToCreate.getAmount());
        transaction.setDescription(transactionsToCreate.getDescription());

        changeSold(transactionsToCreate.getGiverId(), transactionsToCreate.getReceiverId(), transactionsToCreate.getAmount());
        return transactionRepository.save(transaction);
    }

    private void changeSold(Integer giver_id, Integer receiver_id, Float amount) {
        User giver = userRepository.findById(giver_id).orElseThrow();
        User receiver = userRepository.findById(receiver_id).orElseThrow();

        System.out.println("create Transaction by: " + giver.getFirstName() + " for: " + receiver.getFirstName());
        giver.setSold(giver.getSold() - amount);
        receiver.setSold(receiver.getSold() + amount);
        userRepository.save(giver);
        userRepository.save(receiver);
    }

    public User findUserById(Integer id) {
        return userRepository.findById(id).orElseThrow();
    }
}
