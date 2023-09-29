package com.project.paymybuddy.service;

import com.project.paymybuddy.model.AppUser;
import com.project.paymybuddy.model.Transaction;
import com.project.paymybuddy.model.TransactionReadDto;
import com.project.paymybuddy.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionReadServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    TransactionReadService transactionReadService;

    @Test
    void getAllTransactionOk() {
        Transaction transaction = new Transaction();
        AppUser giver = new AppUser();
        AppUser receiver = new AppUser();
        receiver.setFirstName("toto");
        transaction.setAmount(100f);
        transaction.setDescription("test");
        transaction.setReceiverId(receiver);

        when(transactionRepository.findAllByGiverId_Id(any())).thenReturn(List.of(transaction));

        List<TransactionReadDto> transactionReadDtoList = transactionReadService.getAllTransactionRead(giver);
        assertEquals(receiver.getFirstName(), transactionReadDtoList.get(0).getConnexionsName());
        assertEquals(transaction.getAmount(), transactionReadDtoList.get(0).getAmount());
        assertEquals(transaction.getDescription(), transactionReadDtoList.get(0).getDescription());
        assertEquals(1, transactionReadDtoList.size());
    }

    @Test
    void getPagedTransactionOk() {
        Page <Transaction> transactions = Mockito.mock(Page.class);
        Page<TransactionReadDto> transactionReadPage = Mockito.mock(Page.class);
        when(transactionRepository.findAll(any(Pageable.class))).thenReturn(null);

        transactionReadService.getPagedTransactions((Pageable) transactionReadPage);
    }
}
