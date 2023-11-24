package com.project.paymybuddy.service;

import com.project.paymybuddy.exception.UserException;
import com.project.paymybuddy.model.AppUser;
import com.project.paymybuddy.model.Transaction;
import com.project.paymybuddy.model.TransactionReadDto;
import com.project.paymybuddy.repository.TransactionRepository;
import com.project.paymybuddy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TransactionReadService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    private final static Logger LOG = LoggerFactory.getLogger(TransactionReadService.class);

    public List<TransactionReadDto> getAllTransactionRead(AppUser appUser){
        LOG.info("Searching transaction for user: " + appUser.getId());
        List<Transaction> transactions = transactionRepository.findAllByGiverId(appUser.getId());

        List<TransactionReadDto> transactionReadDtoList = new ArrayList<>();

        for (Transaction transaction : transactions) {
            TransactionReadDto dto = new TransactionReadDto();
            String connexionName = userRepository.findById(transaction.getReceiverId()).orElseThrow(
                            () -> new UserException("User not found"))
                    .getFirstName();
            dto.setConnexionsName(connexionName);
            dto.setDescription(transaction.getDescription());
            dto.setAmount(transaction.getAmount());
            transactionReadDtoList.add(dto);
        }

        return transactionReadDtoList;
    }

    public Page<TransactionReadDto> getPagedTransactions(Pageable pageable, AppUser appUser) {
        Page<Transaction> transactionPage = transactionRepository.findAll(pageable);
        List<Transaction> transactionList = transactionPage.stream().filter(transaction ->
                appUser.getId().equals(transaction.getGiverId())).collect(Collectors.toList());
        Page<Transaction> filteredTransaction = new PageImpl<>(transactionList, pageable, transactionList.size());
        return filteredTransaction.map(this::pageTransactionReadDto);
    }


    private TransactionReadDto pageTransactionReadDto(Transaction transaction) {
        TransactionReadDto transactionReadDto = new TransactionReadDto();
        String connexionName = userRepository.findById(transaction.getReceiverId()).orElseThrow(
                        () -> new UserException("User not found"))
                .getFirstName();
        transactionReadDto.setConnexionsName(connexionName);
        transactionReadDto.setAmount(transaction.getAmount());
        transactionReadDto.setDescription(transaction.getDescription());
        return transactionReadDto;
    }
}
