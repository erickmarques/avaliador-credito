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
        return new CustomerCard(ID, CPF, CardUtilTest.createCardDefault(), LIMIT, ENDERECO);
    }

    public static List<CustomerCard> createCustomerCardListDefault(List<Card> cards){
        return List.of(
            new CustomerCard(ID, CPF, cards.get(0), LIMIT, ENDERECO),
            new CustomerCard(2L, CPF, cards.get(1), BigDecimal.valueOf(1600), ENDERECO),
            new CustomerCard(3L, CPF, cards.get(2), BigDecimal.valueOf(1000), ENDERECO),
            new CustomerCard(4L, "CPF_INEXISTINTE", cards.get(2), BigDecimal.valueOf(9999), ENDERECO)
        );
    }

    public static List<CustomerCardResponse> createCustomerCardResponseListDefault(List<CardSaveResponse> cards){
        return List.of(
            new CustomerCardResponse(ID, CPF, LIMIT, cards.get(0))
        );
    }

    public static CardRequestData createCardRequestDataDefault() {
        return new CardRequestData(ID, CPF, ENDERECO, LIMIT);
    }
    
}

