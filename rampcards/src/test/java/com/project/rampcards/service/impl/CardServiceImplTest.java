package com.project.rampcards.service.impl;

import com.project.rampcards.dto.CardDto;
import com.project.rampcards.entity.Card;
import com.project.rampcards.exceptionhandler.CardNotFoundException;
import com.project.rampcards.repository.CardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {
    @Mock
    private CardRepository cardRepository;
    @Spy
    private ModelMapper modelMapper;
    private CardServiceImpl cardServiceImpl;
    AutoCloseable autoCloseable;
    @Mock
    CardDto cardDto;
    @Mock
    Card card;
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        this.cardServiceImpl = new CardServiceImpl(this.cardRepository,this.modelMapper);
        cardDto = new CardDto(1,"Luis");
        card = new Card(1,"Luis");
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void givenCardInfoWhenSaveCardThenSaveCardStatus()
    {
        when(cardRepository.save(card)).thenReturn(card);
        assertThat(cardServiceImpl.saveCard(cardDto)).isEqualTo(card);
    }

    @Test
    void whenFindAllCardThenGetCardStatus() {
        when(cardRepository.findAll()).thenReturn( new ArrayList<Card>(Collections.singleton(card)));
        assertThat(cardServiceImpl.getCard().get(0).getCardId()).isEqualTo(cardDto.getCardId());
        assertThat(cardServiceImpl.getCard().get(0).getType()).isEqualTo(cardDto.getType());
    }

    @Test
    void givenCardIdWhenDeleteCardThenDeleteCardStatus() throws CardNotFoundException {

        when(cardRepository.findById(1)).thenReturn(Optional.of(card));

        String result = cardServiceImpl.deleteCard(1);
        verify(cardRepository).deleteById(1);
        assertThat(result).isEqualTo("Success");
    }


}
