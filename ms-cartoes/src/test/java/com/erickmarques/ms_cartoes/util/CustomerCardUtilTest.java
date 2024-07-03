package com.erickmarques.ms_cartoes.util;

import java.math.BigDecimal;
import java.util.List;

import com.erickmarques.ms_cartoes.application.representation.CardSaveResponse;
import com.erickmarques.ms_cartoes.application.representation.CustomerCardResponse;
import com.erickmarques.ms_cartoes.domain.Card;
import com.erickmarques.ms_cartoes.domain.CardRequestData;
import com.erickmarques.ms_cartoes.domain.CustomerCard;

public class CustomerCardUtilTest {

    public static final Long ID          = 1L;
    public static final String CPF       = "87439818016";
    public static final BigDecimal LIMIT = BigDecimal.valueOf(2000);
    public static final String ENDERECO  = "Rua da Liberdade, 50 - Recife / PE";

    public static CustomerCard createCustomerCardDefault(){
        return CustomerCard
                    .builder()
                    .id(ID)
                    .cpf(CPF)
                    .card(CardUtilTest.createCardDefault())
                    .cardLimit(LIMIT)
                    .address(ENDERECO)
                    .build();
    }

    public static List<CustomerCard> createCustomerCardListDefault(List<Card> cards){
        return List.of(
                    CustomerCard
                        .builder()
                        .id(ID)
                        .cpf(CPF)
                        .card(cards.get(0))
                        .cardLimit(LIMIT)
                        .address(ENDERECO)
                        .build(),

                    CustomerCard
                        .builder()
                        .id(2L)
                        .cpf(CPF)
                        .card(cards.get(1))
                        .cardLimit(BigDecimal.valueOf(1600))
                        .address(ENDERECO)
                        .build(),
                    
                    CustomerCard
                        .builder()
                        .id(3L)
                        .cpf(CPF)
                        .card(cards.get(2))
                        .cardLimit(BigDecimal.valueOf(1000))
                        .address(ENDERECO)
                        .build(), 
                    
                    CustomerCard
                        .builder()
                        .id(4L)
                        .cpf("CPF_INEXISTINTE")
                        .card(cards.get(2))
                        .cardLimit(BigDecimal.valueOf(9999))
                        .address(ENDERECO)
                        .build()
        );
    }

    public static List<CustomerCardResponse> createCustomerCardResponseListDefault(List<CardSaveResponse> cards){
        return List.of(
            CustomerCardResponse
                .builder()
                .id(ID)
                .cpf(CPF)
                .limiteCartao(LIMIT)
                .cartoes(cards.get(0))
                .build()
        );
    }

    public static CardRequestData createCardRequestDataDefault() {
        return CardRequestData
                .builder()
                .idCard(ID)
                .cpf(CPF)
                .address(ENDERECO)
                .limitReleased(LIMIT)
                .build();
    }
    
}

