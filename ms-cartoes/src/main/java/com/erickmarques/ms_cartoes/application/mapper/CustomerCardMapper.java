package com.erickmarques.ms_cartoes.application.mapper;

import org.springframework.stereotype.Component;

import com.erickmarques.ms_cartoes.application.representation.CustomerCardResponse;
import com.erickmarques.ms_cartoes.domain.Card;
import com.erickmarques.ms_cartoes.domain.CardRequestData;
import com.erickmarques.ms_cartoes.domain.CustomerCard;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerCardMapper {

    private final CardMapper cardMapper;

    public CustomerCard toEntity(CardRequestData cardRequestData, Card card){

        return CustomerCard.builder()
                .card(card)
                .cpf(cardRequestData.getCpf())
                .address(cardRequestData.getAddress())
                .cardLimit(cardRequestData.getLimitReleased())
                .build();
    }
    
    public CustomerCardResponse toDto(CustomerCard customerCard){

        return CustomerCardResponse.builder()
                .id(customerCard.getId())
                .cpf(customerCard.getCpf())
                .limiteCartao(customerCard.getCardLimit())
                .cartoes(cardMapper.toDto(customerCard.getCard()))
                .build();
    }
}
