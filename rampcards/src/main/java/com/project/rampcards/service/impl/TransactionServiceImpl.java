package com.project.rampcards.service.impl;

import com.project.rampcards.dto.TransactionDto;
import com.project.rampcards.entity.Quickbook;
import com.project.rampcards.entity.Transaction;
import com.project.rampcards.exceptionhandler.QuickbookNotFoundException;
import com.project.rampcards.exceptionhandler.TransactionNotFoundException;
import com.project.rampcards.repository.QuickbookRepository;
import com.project.rampcards.repository.TransactionRepository;
import com.project.rampcards.service.TransactionService;
import jakarta.transaction.Transactional;
import java.util.Collections;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private QuickbookRepository quickbookRepository;
    private ModelMapper modelMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, ModelMapper modelMapper,QuickbookRepository quickbookRepository) {
        this.transactionRepository=transactionRepository;
        this.modelMapper = modelMapper;
        this.quickbookRepository = quickbookRepository;
    }

    @Override
    public List<TransactionDto> getTransaction() {
        List<Transaction> transactionList = transactionRepository.findAll();
        return transactionList.stream().map(Transaction -> modelMapper.map(Transaction,TransactionDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> getTransactionByName(String name) throws TransactionNotFoundException {
        if(! transactionRepository.findAllByTransactionName(name).isEmpty()) {
        List<Transaction> transactionList1 = transactionRepository.findAllByTransactionName(name);
        return transactionList1.stream().map(Transaction -> modelMapper.map(Transaction, TransactionDto.class)).collect(Collectors.toList());
        }
        throw new TransactionNotFoundException("Transaction with name "+ name +" not found");
    }

    @Override
    @Transactional
    public Transaction saveTransaction(TransactionDto transactionDto) {
    return transactionRepository.save(modelMapper.map(transactionDto,Transaction.class));
    }

    @Override
    public String deleteTransaction(int id) throws TransactionNotFoundException{
        if(transactionRepository.findById(id).isPresent())
        {
            TransactionDto transactionDto = modelMapper.map(transactionRepository.findById(id).get(), TransactionDto.class);
            transactionRepository.deleteById(transactionDto.getId());
            return "Success";
        }
        throw new TransactionNotFoundException("Transaction with id "+ id +" not found");
    }

/*    In update operation we are trying to patch quickbook of transaction on the basis of category entered by user
      checking by transaction id if present
      getting transaction details in transaction object
      checking if quickbook in transaction is null or not and category we want to update is already same or not
      getting quickbook details in quickbook object from category
      checking quickbook object if it is null or not ( category belongs to pre-defined one or not)
      if all yes than update quickbook in transaction or return the message to user for success/failure    */

    @Override
    @Transactional
    public String updateTransaction(int id, String category) throws TransactionNotFoundException, QuickbookNotFoundException {
        if(transactionRepository.findById(id).isPresent())
        {
            Transaction transaction = transactionRepository.findById(id).get();
            if( transaction.getQuickbook().getCategory() != null && !transaction.getQuickbook().getCategory().equals(category))
            {
                if(quickbookRepository.findByCategory(category) != null) {
                    Quickbook quickbook = quickbookRepository.findByCategory(category);
                    transaction.setQuickbook(quickbook);
                    return "Quickbook Category is updated";
                }
                throw new QuickbookNotFoundException("Quickbook with category "+ category +" not found");
            }
            return "Quickbook Category is same";
        }
        throw new TransactionNotFoundException("Transaction with id "+ id +" not found");
    }
}
