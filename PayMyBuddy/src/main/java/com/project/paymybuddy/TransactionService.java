package com.project.paymybuddy;

import com.project.paymybuddy.model.Transactions;
import com.project.paymybuddy.model.User;
import com.project.paymybuddy.repository.TransactionsRepository;
import com.project.paymybuddy.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
public class TransactionService {

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    UserRepository userRepository;

    public Transactions createTransactions(Integer giver, Integer receiver, Integer amount, @Nullable String description) {
        Transactions transactions = new Transactions();
        transactions.setDate(new Date());
        transactions.setGiver(findUserById(giver));
        transactions.setReceiver(findUserById(receiver));
        transactions.setAmount(amount);
        transactions.setDescription(description);

        changeSold(giver, receiver, amount);
        return transactionsRepository.save(transactions);
    }

    private void changeSold(Integer giver_id, Integer receiver_id, Integer amount) {
        User giver = userRepository.findById(giver_id).orElseThrow();
        User receiver = userRepository.findById(receiver_id).orElseThrow();

        System.out.println("create Transaction by: " + giver.getFirstName() + " for: " + receiver.getFirstName());
        giver.setSold(giver.getSold() - amount);
        receiver.setSold(receiver.getSold() + amount);
    }

    public User findUserById(Integer id) {
        return userRepository.findById(id).orElseThrow();
    }
}
