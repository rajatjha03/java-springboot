package com.project.rampcards.service.impl;

import com.project.rampcards.dto.CardDto;
import com.project.rampcards.entity.Card;
import com.project.rampcards.exceptionhandler.CardNotFoundException;
import com.project.rampcards.repository.CardRepository;
import com.project.rampcards.service.CardService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;
    private ModelMapper modelMapper;

    public CardServiceImpl(CardRepository cardRepository, ModelMapper modelMapper) {
        this.cardRepository = cardRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CardDto> getCard() {
        List<Card> cards = cardRepository.findAll();
        return cards.stream().map(card -> modelMapper.map(card,CardDto.class)).collect(Collectors.toList());
    }

    @Override
    public CardDto getCardById(int id) throws CardNotFoundException {
        if(cardRepository.findById(id).isPresent())
        {
            return modelMapper.map(cardRepository.findById(id).get(),CardDto.class);

        }
        throw new CardNotFoundException("Card with id "+ id +" not found");
    }

    @Override
    @Transactional
    public Card saveCard(CardDto cardDto) {
       return cardRepository.save(this.modelMapper.map(cardDto,Card.class));
    }

    @Override
    public String deleteCard(int id) throws CardNotFoundException {
        if(cardRepository.findById(id).isPresent())
        {
            CardDto cardDto = modelMapper.map(cardRepository.findById(id).get(), CardDto.class);
            cardRepository.deleteById(cardDto.getCardId());
            return "Success";
        }
        throw new CardNotFoundException("Card with id "+ id +" not found");
    }
}
