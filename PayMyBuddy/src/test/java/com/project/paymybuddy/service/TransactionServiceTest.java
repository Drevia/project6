package com.project.paymybuddy.service;

import com.project.paymybuddy.exception.UserException;
import com.project.paymybuddy.model.AppUser;
import com.project.paymybuddy.model.Transaction;
import com.project.paymybuddy.model.TransactionsDto;
import com.project.paymybuddy.repository.TransactionRepository;
import com.project.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {


    @InjectMocks
    TransactionService transactionService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    UserRepository userRepository;

    @Test
    void createTransactionOk() {
        AppUser giver =  new AppUser();
        giver.setId(1);
        giver.setSold(50f);
        AppUser receiver = new AppUser();
        receiver.setId(2);
        receiver.setSold(50f);

        when(userRepository.findById(1)).thenReturn(Optional.of(giver));
        when(userRepository.findById(2)).thenReturn(Optional.of(receiver));
        when(userRepository.save(giver)).thenReturn(giver);
        when(userRepository.save(receiver)).thenReturn(receiver);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        TransactionsDto transactionsDto = new TransactionsDto();
        transactionsDto.setAmount(10f);
        transactionsDto.setGiverId(1);
        transactionsDto.setReceiverId(2);
        transactionsDto.setDescription("test");

        transactionService.createTransactions(transactionsDto);
        assertEquals(40f, giver.getSold());
        assertEquals(60f,receiver.getSold());

    }

    @Test
    void createTransactionGiverNotFound() {
        TransactionsDto transactionsDto = new TransactionsDto();
        transactionsDto.setAmount(10f);
        transactionsDto.setGiverId(1);
        transactionsDto.setReceiverId(2);

        when(userRepository.findById(1)).thenThrow(new UserException("Giver not found"));

        assertThrows(UserException.class, () -> transactionService.createTransactions(transactionsDto));
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }

    @Test
    void createTransactionReceiverNotFound() {
        AppUser giver =  new AppUser();
        giver.setId(1);
        giver.setSold(50f);
        TransactionsDto transactionsDto = new TransactionsDto();
        transactionsDto.setAmount(10f);
        transactionsDto.setGiverId(1);
        transactionsDto.setReceiverId(2);

        when(userRepository.findById(1)).thenReturn(Optional.of(giver));
        when(userRepository.findById(2)).thenThrow(new UserException("Receiver not found"));

        assertThrows(UserException.class, () -> transactionService.createTransactions(transactionsDto));
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }

    @Test
    void createTransaction_NoAmount_ThrowError() {
        AppUser giver =  new AppUser();
        giver.setId(1);
        giver.setSold(50f);
        AppUser receiver = new AppUser();
        receiver.setId(2);
        receiver.setSold(50f);

        TransactionsDto transactionsDto = new TransactionsDto();
        transactionsDto.setAmount(0f);
        transactionsDto.setGiverId(1);
        transactionsDto.setReceiverId(2);

        when(userRepository.findById(1)).thenReturn(Optional.of(giver));
        when(userRepository.findById(2)).thenReturn(Optional.of(receiver));
        assertThrows(UserException.class, () -> transactionService.createTransactions(transactionsDto));
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }

    @Test
    void createTransaction_GiverAmountNotCorrect_ThrowError() {
        AppUser giver =  new AppUser();
        giver.setId(1);
        giver.setSold(0f);
        AppUser receiver = new AppUser();
        receiver.setId(2);
        receiver.setSold(50f);

        TransactionsDto transactionsDto = new TransactionsDto();
        transactionsDto.setAmount(10f);
        transactionsDto.setGiverId(1);
        transactionsDto.setReceiverId(2);

        when(userRepository.findById(1)).thenReturn(Optional.of(giver));
        when(userRepository.findById(2)).thenReturn(Optional.of(receiver));
        assertThrows(UserException.class, () -> transactionService.createTransactions(transactionsDto));
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }

    @Test
    void createTransaction_UserNotFound_ThrowError() {
        TransactionsDto transactionsDto = new TransactionsDto();
        transactionsDto.setAmount(0f);
        transactionsDto.setGiverId(1);
        transactionsDto.setReceiverId(2);

        when(userRepository.findById(any())).thenThrow(UserException.class);

        UserException exception = assertThrows(UserException.class, () -> transactionService.createTransactions(transactionsDto));
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }

    @Test
    void createTransaction_amountNotCorrect_throwException() {
        AppUser giver =  new AppUser();
        giver.setId(1);
        giver.setSold(0f);
        AppUser receiver = new AppUser();
        receiver.setId(2);
        receiver.setSold(50f);

        when(userRepository.findById(1)).thenReturn(Optional.of(giver));
        when(userRepository.findById(2)).thenReturn(Optional.of(receiver));

        TransactionsDto transactionsDto = new TransactionsDto();
        transactionsDto.setAmount(-10f);
        transactionsDto.setGiverId(1);
        transactionsDto.setReceiverId(2);
        transactionsDto.setDescription("test");

        assertThrows(UserException.class, () -> transactionService.createTransactions(transactionsDto));
        verify(transactionRepository, times(0)).save(any(Transaction.class));
        verify(userRepository, times(0)).save(any(AppUser.class));

    }

}
