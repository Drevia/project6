package com.project.paymybuddy.service;

import com.project.paymybuddy.exception.UserException;
import com.project.paymybuddy.model.AppUser;
import com.project.paymybuddy.model.Transaction;
import com.project.paymybuddy.model.TransactionReadDto;
import com.project.paymybuddy.repository.TransactionRepository;
import com.project.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionReadServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    TransactionReadService transactionReadService;

    @Test
    void getAllTransactionOk() {
        Transaction transaction = new Transaction();
        AppUser giver = new AppUser();
        giver.setId(1);
        AppUser receiver = new AppUser();
        receiver.setFirstName("toto");
        receiver.setId(2);
        transaction.setAmount(100f);
        transaction.setDescription("test");
        transaction.setReceiverId(receiver.getId());

        when(transactionRepository.findAllByGiverId(any())).thenReturn(List.of(transaction));
        when(userRepository.findById(any())).thenReturn(Optional.of(receiver));

        List<TransactionReadDto> transactionReadDtoList = transactionReadService.getAllTransactionRead(giver);
        assertEquals(receiver.getFirstName(), transactionReadDtoList.get(0).getConnexionsName());
        assertEquals(transaction.getAmount(), transactionReadDtoList.get(0).getAmount());
        assertEquals(transaction.getDescription(), transactionReadDtoList.get(0).getDescription());
        assertEquals(1, transactionReadDtoList.size());
    }

    @Test
    void getAllTransaction_userNotFound() {
        Transaction transaction = new Transaction();
        AppUser giver = new AppUser();
        giver.setId(1);
        AppUser receiver = new AppUser();
        receiver.setFirstName("toto");
        receiver.setId(2);
        transaction.setAmount(100f);
        transaction.setDescription("test");
        transaction.setReceiverId(receiver.getId());

        when(transactionRepository.findAllByGiverId(any())).thenReturn(List.of(transaction));

        UserException exception = assertThrows(UserException.class,
                () -> transactionReadService.getAllTransactionRead(giver));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void getPagedTransactionOk() {
        Transaction transaction = new Transaction();
        AppUser giver = new AppUser();
        giver.setId(1);
        AppUser receiver = new AppUser();
        receiver.setId(2);
        receiver.setFirstName("toto");
        transaction.setAmount(100f);
        transaction.setDescription("test");
        transaction.setGiverId(giver.getId());
        transaction.setReceiverId(receiver.getId());

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        Page<Transaction>transactionPage = new PageImpl<>(transactions);

        when(transactionRepository.findAll(any(Pageable.class))).thenReturn(transactionPage);
        when(userRepository.findById(any())).thenReturn(Optional.of(receiver));

        Page<TransactionReadDto> result = transactionReadService.getPagedTransactions(PageRequest.of(0, 3));

        assertNotNull(result);
    }

    @Test
    void getPagedTransaction_UserNotFound() {
        Transaction transaction = new Transaction();
        AppUser giver = new AppUser();
        giver.setId(1);
        AppUser receiver = new AppUser();
        receiver.setId(2);
        receiver.setFirstName("toto");
        transaction.setAmount(100f);
        transaction.setDescription("test");
        transaction.setGiverId(giver.getId());
        transaction.setReceiverId(receiver.getId());

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        Page<Transaction>transactionPage = new PageImpl<>(transactions);

        when(transactionRepository.findAll(any(Pageable.class))).thenReturn(transactionPage);

        UserException exception = assertThrows(UserException.class,
                () -> transactionReadService.getPagedTransactions(PageRequest.of(0, 3)));

        assertEquals("User not found", exception.getMessage());
    }
}
