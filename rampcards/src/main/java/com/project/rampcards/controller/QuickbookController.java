package com.project.rampcards.controller;

import com.project.rampcards.dto.QuickbookDto;
import com.project.rampcards.entity.Quickbook;
import com.project.rampcards.exceptionhandler.QuickbookNotFoundException;
import com.project.rampcards.service.QuickbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ramp-card/v1/quickbook")
public class QuickbookController {
    @Autowired
    private QuickbookService quickbookService;

    @GetMapping("/")
    public ResponseEntity<List<QuickbookDto>> showQuickbook(){
        return new ResponseEntity<>(quickbookService.getQuickbook(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Quickbook> addQuickbook(@RequestBody QuickbookDto quickbookDto){
        return new ResponseEntity<>(quickbookService.saveQuickbook(quickbookDto),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuickbook(@PathVariable int id) throws QuickbookNotFoundException {
        return new ResponseEntity<>(quickbookService.deleteQuickbook(id),HttpStatus.OK);
    }
}
