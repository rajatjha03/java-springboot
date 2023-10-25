package com.project.rampcards.service;

import com.project.rampcards.dto.TransactionDto;
import com.project.rampcards.entity.Transaction;
import com.project.rampcards.exceptionhandler.QuickbookNotFoundException;
import com.project.rampcards.exceptionhandler.TransactionNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TransactionService {
    List<TransactionDto> getTransaction();

    List<TransactionDto> getTransactionByName(String name) throws TransactionNotFoundException;

    Transaction saveTransaction(TransactionDto transactionDto);

    String deleteTransaction(int id) throws TransactionNotFoundException;

    String updateTransaction(int id,String category) throws TransactionNotFoundException, QuickbookNotFoundException;
}
