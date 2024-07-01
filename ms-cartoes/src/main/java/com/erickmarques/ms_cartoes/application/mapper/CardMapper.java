package com.erickmarques.ms_cartoes.application.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.erickmarques.ms_cartoes.application.representation.CardSaveRequest;
import com.erickmarques.ms_cartoes.application.representation.CardSaveResponse;
import com.erickmarques.ms_cartoes.domain.Card;
import com.erickmarques.ms_cartoes.domain.CardFlag;

@Component
public class CardMapper {

    public Card toEntity(CardSaveRequest cardSaveRequest){

        CardFlag cardFlag = stringToCardFlag(cardSaveRequest.getBandeiraCartao());

        return Card.builder()
                .name(cardSaveRequest.getNome())
                .cardFlag(cardFlag)
                .income(cardSaveRequest.getRenda())
                .basicLimit(cardSaveRequest.getLimiteBasico())
                .build();
    }

    private CardFlag stringToCardFlag(String cardFlagDto){
        CardFlag cardFlag = null;

        try {
            cardFlag = CardFlag.valueOf(cardFlagDto.toUpperCase());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Favor informar uma bandeira de cartão válida!");
        }

        return cardFlag;
    }
    
    public CardSaveResponse toDto(Card card){
        
        return CardSaveResponse.builder()
                .id(card.getId())
                .nome(card.getName())
                .bandeiraCartao(card.getCardFlag())
                .renda(card.getIncome())
                .limiteBasico(card.getBasicLimit())
                .build();
    }
    
}
