package com.project.rampcards.service.impl;

import com.project.rampcards.dto.TransactionDto;
import com.project.rampcards.entity.Card;
import com.project.rampcards.entity.Quickbook;
import com.project.rampcards.entity.Transaction;
import com.project.rampcards.entity.User;
import com.project.rampcards.exceptionhandler.QuickbookNotFoundException;
import com.project.rampcards.exceptionhandler.TransactionNotFoundException;
import com.project.rampcards.repository.QuickbookRepository;
import com.project.rampcards.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    TransactionRepository transactionRepository;
    @Mock
    QuickbookRepository quickbookRepository;
    @Spy
    ModelMapper modelMapper;
    @InjectMocks
    TransactionServiceImpl transactionServiceImpl;
    AutoCloseable autoCloseable;
    @Mock
    Transaction transaction;
    @Mock
    TransactionDto transactionDto;
    User user;
    Quickbook quickbook,quickbook1;
    Card card;

    @BeforeEach
    void setUp() {
        user = new User(1,"Ab","xyz");
        quickbook = new Quickbook(1,"Travel");
        quickbook1 = new Quickbook(1,"expense");
        card = new Card(1,"Luis");
        autoCloseable = MockitoAnnotations.openMocks(this);
        this.transactionServiceImpl= new TransactionServiceImpl(this.transactionRepository,this.modelMapper,this.quickbookRepository);
        transaction = new Transaction(1,"bill",7000.0, LocalDate.of(2022,8,12)
                ,"aaa",5760000.0,new User(1,"Ab","xyz"),new Quickbook(1,"expense"),new Card(1,"Luis"));
        transactionDto = new TransactionDto(1,"bill",7000.0, LocalDate.of(2022,8,12)
                ,"aaa",5760000.0,1,1,1);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void whenFindAllTransactionThenGetAllTransactionStatus() {
        when(transactionRepository.findAll()).thenReturn( new ArrayList<Transaction>(Collections.singleton(transaction)));
        assertThat(transactionServiceImpl.getTransaction().get(0).getId()).isEqualTo(transactionDto.getId());
        assertThat(transactionServiceImpl.getTransaction().get(0).getName()).isEqualTo(transactionDto.getName());
    }

    @Test
    void whenFindTransactionByNameThenGetTransactionByNameStatus() throws TransactionNotFoundException {
        when(transactionRepository.findAllByTransactionName(transaction.getName())).thenReturn( new ArrayList<Transaction>(Collections.singleton(transaction)));
        assertThat(transactionServiceImpl.getTransactionByName(transaction.getName()).get(0).getId()).isEqualTo(transactionDto.getId());
        assertThat(transactionServiceImpl.getTransactionByName(transaction.getName()).get(0).getName()).isEqualTo(transactionDto.getName());
    }

    @Test
    void givenTransactionInfoWhenSaveTransactionThenSaveTransactionStatus()
    {
        when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(transaction);
        assertThat(transactionServiceImpl.saveTransaction(transactionDto)).isEqualTo(transaction);
    }

    @Test
    void givenTransactionIdWhenDeleteTransactionThenDeleteTransactionStatus() throws TransactionNotFoundException {
        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));

        String result = transactionServiceImpl.deleteTransaction(1);
        verify(transactionRepository).deleteById(1);
        assertThat(result).isEqualTo("Success");
    }

    @Test
    void givenTransactionIdAndDifferentCategoryWhenUpdateCategoryThenUpdateCategoryStatus() throws QuickbookNotFoundException, TransactionNotFoundException {

        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));
        when(quickbookRepository.findByCategory(quickbook.getCategory())).thenReturn(quickbook);

        String result = transactionServiceImpl.updateTransaction(quickbook.getId(),quickbook.getCategory());
        assertThat(result).isEqualTo("Quickbook Category is updated");
    }

    @Test
    void givenTransactionIdAndSameCategoryWhenUpdateCategoryThenUpdateCategoryStatus() throws QuickbookNotFoundException, TransactionNotFoundException {

        Mockito.lenient().when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));
        Mockito.lenient().when(quickbookRepository.findByCategory(quickbook1.getCategory())).thenReturn(quickbook1);

        String result = transactionServiceImpl.updateTransaction(1,"expense");
        assertThat(result).isEqualTo("Quickbook Category is same");
    }

}
