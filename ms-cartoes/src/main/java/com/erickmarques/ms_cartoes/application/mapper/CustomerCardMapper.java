package com.erickmarques.ms_cartoes.application.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.erickmarques.ms_cartoes.application.representation.CustomerCardResponse;
import com.erickmarques.ms_cartoes.domain.CustomerCard;

@Component
public class CustomerCardMapper {

    @Autowired
    private CardMapper cardMapper;
    
    public CustomerCardResponse toDto(CustomerCard customerCard){

        CustomerCardResponse customerCardResponse = new CustomerCardResponse();

        customerCardResponse.setId(customerCard.getId());
        customerCardResponse.setCpf(customerCard.getCpf());
        customerCardResponse.setLimiteCartao(customerCard.getCardLimit());
        customerCardResponse.setCartoes(cardMapper.toDto(customerCard.getCard()));

        return customerCardResponse;
    }
}
