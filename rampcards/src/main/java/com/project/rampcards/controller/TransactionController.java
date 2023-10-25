package com.project.rampcards.controller;

import com.project.rampcards.dto.TransactionDto;
import com.project.rampcards.entity.Transaction;
import com.project.rampcards.exceptionhandler.QuickbookNotFoundException;
import com.project.rampcards.exceptionhandler.TransactionNotFoundException;
import com.project.rampcards.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ramp-card/v1/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/")
    public ResponseEntity<List<TransactionDto>> showTransaction()
    {
        return new ResponseEntity<>(transactionService.getTransaction(), HttpStatus.OK);
    }
    @GetMapping("/{name}")
    public ResponseEntity<List<TransactionDto>> showTransactionByName(@PathVariable(value="name") String name) throws TransactionNotFoundException
    {
        return new ResponseEntity<>(transactionService.getTransactionByName(name), HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<Transaction> addTransaction(@RequestBody TransactionDto transactionDto)
    {

        return new ResponseEntity<>(transactionService.saveTransaction(transactionDto),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable(value="id") int id) throws TransactionNotFoundException
    {
        return new ResponseEntity<>(transactionService.deleteTransaction(id),HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> alterTransaction(@PathVariable(value="id") int id,@RequestParam(value="category") String category) throws QuickbookNotFoundException, TransactionNotFoundException {
        return new ResponseEntity<>(transactionService.updateTransaction(id,category),HttpStatus.CREATED);
    }

}
