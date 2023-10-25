package com.project.rampcards.controller;

import com.project.rampcards.dto.CardDto;
import com.project.rampcards.entity.Card;
import com.project.rampcards.exceptionhandler.CardNotFoundException;
import com.project.rampcards.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardControllerTest {

    @InjectMocks
    private CardController cardController;

    @Mock
    private CardService cardService;

    List<CardDto> cardDtoList;
    int cardId;
    String message;
    CardDto cardDto;
    Card card;
    AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() {
        cardId = 1;
        message = "Success";
        autoCloseable = MockitoAnnotations.openMocks(this);
        cardDtoList = new ArrayList<>();
        cardDtoList.add(new CardDto(1,"luis"));
        cardDtoList.add(new CardDto(2,"phillipe"));
        cardDto = new CardDto(1,"lee");
        card = new Card(1, "lee");
    }

    @Test
    void whenShowAllCardThenGetAllCardStatus() {
        when(cardService.getCard()).thenReturn(cardDtoList);

        ResponseEntity<List<CardDto>> response = cardController.showCard();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(cardDtoList).isEqualTo(response.getBody());
    }

    @Test
    void givenCardInfoWhenAddCardThenAddCardStatus(){
        when(cardService.saveCard(cardDto)).thenReturn(card);

        ResponseEntity<Card> response = cardController.addCard(cardDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(card).isEqualTo(response.getBody());
    }

    @Test
    void givenCardIdWhenRemoveCardThenRemoveCardStatus() throws CardNotFoundException {
        when(cardService.deleteCard(cardId)).thenReturn(message);

        ResponseEntity<String> response = cardController.deleteCard(cardId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(message).isEqualTo(response.getBody());
    }
}
