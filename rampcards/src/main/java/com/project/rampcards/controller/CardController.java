package com.project.rampcards.controller;

import com.project.rampcards.dto.CardDto;
import com.project.rampcards.entity.Card;
import com.project.rampcards.exceptionhandler.CardNotFoundException;
import com.project.rampcards.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ramp-card/v1/card")
@ComponentScan
public class CardController {
    @Autowired
    private CardService cardService;

    @GetMapping("/")
    public ResponseEntity<List<CardDto>> showCard(){
        return new ResponseEntity<>(cardService.getCard(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDto> showCardById(@PathVariable int id) throws CardNotFoundException{
        return new ResponseEntity<>(cardService.getCardById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Card> addCard(@RequestBody CardDto cardDto){
        return new ResponseEntity<>(cardService.saveCard(cardDto),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCard(@PathVariable int id) throws CardNotFoundException {
        return new ResponseEntity<>(cardService.deleteCard(id),HttpStatus.OK);
    }

}
