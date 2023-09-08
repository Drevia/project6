package com.project.paymybuddy.service;

import com.project.paymybuddy.model.AppUser;
import com.project.paymybuddy.model.Friendship;
import com.project.paymybuddy.model.Transaction;
import com.project.paymybuddy.model.TransactionReadDto;
import com.project.paymybuddy.repository.FriendshipRepository;
import com.project.paymybuddy.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionReadService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    FriendshipRepository friendshipRepository;

    public List<TransactionReadDto> getAllTransactionRead(AppUser appUser){
        List<Transaction> transactions = transactionRepository.findAllByGiverId_Id(appUser.getId());

        List<TransactionReadDto> transactionReadDtoList = new ArrayList<>();

        for (Transaction transaction : transactions) {
            TransactionReadDto dto = new TransactionReadDto();
            dto.setConnexionsName(transaction.getReceiverId().getFirstName());
            dto.setDescription(transaction.getDescription());
            dto.setAmount(transaction.getAmount());
            transactionReadDtoList.add(dto);
        }

        return transactionReadDtoList;
    }
}