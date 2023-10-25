package com.project.rampcards.service;

import com.project.rampcards.dto.CardDto;
import com.project.rampcards.entity.Card;
import com.project.rampcards.exceptionhandler.CardNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CardService {
    public List<CardDto> getCard();
    public CardDto getCardById(int id) throws CardNotFoundException;

    public Card saveCard(CardDto cardDto);
    public String deleteCard(int id) throws CardNotFoundException;
}
