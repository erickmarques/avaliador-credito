package com.erickmarques.ms_cartoes.util;

import java.math.BigDecimal;
import java.util.List;

import com.erickmarques.ms_cartoes.domain.Card;
import com.erickmarques.ms_cartoes.domain.CustomerCard;

public class CustomerCardUtilTest {

    public static final Long ID          = 1L;
    public static final String CPF       = "87439818016";
    public static final BigDecimal LIMIT = BigDecimal.valueOf(2000);

    public static CustomerCard createCustomerCardDefault(){
        return new CustomerCard(ID, CPF, CardUtilTest.createCardDefault(), LIMIT);
    }

    public static List<CustomerCard> createCustomerCardListDefault(List<Card> cards){
        return List.of(
            new CustomerCard(ID, CPF, cards.get(0), LIMIT),
            new CustomerCard(2L, CPF, cards.get(1), BigDecimal.valueOf(1600)),
            new CustomerCard(3L, CPF, cards.get(2), BigDecimal.valueOf(1000)),
            new CustomerCard(4L, "CPF_INEXISTINTE", cards.get(2), BigDecimal.valueOf(9999))
        );
    }
    
}

