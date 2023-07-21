package com.project.paymybuddy;

import com.project.paymybuddy.repository.TransactionsRepository;
import com.project.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    UserRepository userRepository;
}
