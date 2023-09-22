package com.project.paymybuddy.service;

import com.project.paymybuddy.model.AppUser;
import com.project.paymybuddy.model.Transaction;
import com.project.paymybuddy.model.TransactionReadDto;
import com.project.paymybuddy.repository.FriendshipRepository;
import com.project.paymybuddy.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionReadService {

    @Autowired
    private TransactionRepository transactionRepository;

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

    public Page<TransactionReadDto> getPagedTransactions(Pageable pageable) {
        Page<Transaction> transactionPage = transactionRepository.findAll(pageable);
        return transactionPage.map(this::pageTransactionReadDto);
    }


    private TransactionReadDto pageTransactionReadDto(Transaction transaction) {
        TransactionReadDto transactionReadDto = new TransactionReadDto();
        transactionReadDto.setConnexionsName(transaction.getReceiverId().getFirstName());
        transactionReadDto.setAmount(transaction.getAmount());
        transactionReadDto.setDescription(transaction.getDescription());
        return transactionReadDto;
    }
}
