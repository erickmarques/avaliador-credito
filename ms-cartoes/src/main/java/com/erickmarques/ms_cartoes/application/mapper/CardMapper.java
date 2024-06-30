package com.erickmarques.ms_cartoes.application.mapper;

import org.springframework.stereotype.Component;

import com.erickmarques.ms_cartoes.application.representation.CardSaveRequest;
import com.erickmarques.ms_cartoes.application.representation.CardSaveResponse;
import com.erickmarques.ms_cartoes.domain.Card;

@Component
public class CardMapper {

    public Card toEntity(CardSaveRequest cardSaveRequest){
        Card card = new Card();

        card.setName(cardSaveRequest.getName());
        card.setCardFlag(cardSaveRequest.getCardFlag());
        card.setIncome(cardSaveRequest.getIncome());
        card.setBasicLimit(cardSaveRequest.getBasicLimit());

        return card;
    }
    
    public CardSaveResponse toDto(Card card){
        
        CardSaveResponse cardSaveResponse = new CardSaveResponse();

        cardSaveResponse.setId(card.getId());
        cardSaveResponse.setName(card.getName());
        cardSaveResponse.setCardFlag(card.getCardFlag());
        cardSaveResponse.setIncome(card.getIncome());
        cardSaveResponse.setBasicLimit(card.getBasicLimit());

        return cardSaveResponse;
    }
    
}
