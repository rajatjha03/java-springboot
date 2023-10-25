package com.project.rampcards.controller;

import com.project.rampcards.dto.TransactionDto;
import com.project.rampcards.entity.Card;
import com.project.rampcards.entity.Quickbook;
import com.project.rampcards.entity.Transaction;
import com.project.rampcards.entity.User;
import com.project.rampcards.exceptionhandler.TransactionNotFoundException;
import com.project.rampcards.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @InjectMocks
    TransactionController transactionController;
    @Mock
    TransactionService transactionService;
    List<TransactionDto> transactionDtoList;
    String transactionName;

    AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        transactionDtoList = new ArrayList<>();
        transactionDtoList.add(new TransactionDto(1,"bill",7000.0, LocalDate.of(2022,8,12)
                ,"#aaa",5760000.0,1,1,1));
        transactionDtoList.add(new TransactionDto(1,"shopping",9000.0, LocalDate.of(2022,10,19)
                ,"#bbb",5761111.0,2,2,2));
        String transactionName = "bill";
    }

    @Test
    void whenShowAllTransactionThenGetAllTransactionStatus() {
        when(transactionService.getTransaction()).thenReturn(transactionDtoList);

        ResponseEntity<List<TransactionDto>> response = transactionController.showTransaction();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(transactionDtoList).isEqualTo(response.getBody());
    }

    @Test
    void givenTransactionNameWhenShowAllTransactionThenGetAllTransactionStatus() throws TransactionNotFoundException {
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        transactionDtoList.add(new TransactionDto(1,"bill",7000.0, LocalDate.of(2022,8,12)
                ,"#aaa",5760000.0,1,1,1));

        when(transactionService.getTransactionByName(transactionName)).thenReturn(transactionDtoList);

        ResponseEntity<List<TransactionDto>> response = transactionController.showTransactionByName(transactionName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(transactionDtoList).isEqualTo(response.getBody());
    }

    @Test
    void givenTransactionInfoWhenAddTransactionThenAddTransactionStatus(){
        Transaction transaction = new Transaction(1,"bill",7000.0, LocalDate.of(2022,8,12)
                ,"aaa",5760000.0,new User(1,"Ab","xyz"),new Quickbook(1,"expense"),new Card(1,"Luis"));
        TransactionDto transactionDto = new TransactionDto(1,"bill",7000.0, LocalDate.of(2022,8,12)
                ,"aaa",5760000.0,1,1,1);

        when(transactionService.saveTransaction(transactionDto)).thenReturn(transaction);

        ResponseEntity<Transaction> response = transactionController.addTransaction(transactionDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transaction, response.getBody());
    }

    @Test
    void givenTransactionIdWhenRemoveTransactionThenRemoveTransactionStatus() throws TransactionNotFoundException {
        int transactionId = 1;
        String message = "Success";

        when(transactionService.deleteTransaction(transactionId)).thenReturn(message);

        ResponseEntity<String> response = transactionController.deleteTransaction(transactionId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(message).isEqualTo(response.getBody());
    }

    //negative test cases

    @Test
    void givenInvalidTransactionNameWhenShowAllTransactionThenGetAllTransactionStatus() throws TransactionNotFoundException {
        String transactionName = "Grocery";
        when(transactionService.getTransactionByName(transactionName)).thenThrow(TransactionNotFoundException.class);

        assertThrows(TransactionNotFoundException.class, () -> {
            transactionController.showTransactionByName(transactionName); });

  /*      ResponseEntity<UserDto> response = userController.showUserById(userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);*/
    }

    @Test
    void givenInvalidTransactionIdWhenDeleteUserThenDeleteUserStatus() throws TransactionNotFoundException {
        int transactionId = 8;
        when(transactionService.deleteTransaction(transactionId)).thenThrow(TransactionNotFoundException.class);


        assertThrows(TransactionNotFoundException.class, () -> {
            transactionController.deleteTransaction(transactionId);
/*        ResponseEntity<String> response = userController.deleteUser(userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);*/
        });
    }

}
