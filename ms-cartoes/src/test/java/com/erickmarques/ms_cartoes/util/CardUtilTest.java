package com.erickmarques.ms_cartoes.util;

import java.math.BigDecimal;
import java.util.List;

import com.erickmarques.ms_cartoes.domain.Card;
import com.erickmarques.ms_cartoes.domain.CardFlag;

public class CardUtilTest {

    public static final Long ID                = 1L;
    public static final String NAME            = "BLACK";
    public static final CardFlag MASTERCARD    = CardFlag.MASTERCARD;
    public static final BigDecimal INCOME      = BigDecimal.valueOf(6000);
    public static final BigDecimal BASIC_LIMIT = BigDecimal.valueOf(1800);

    public static Card createCardDefault(){
        return new Card(ID, NAME, MASTERCARD, INCOME, BASIC_LIMIT);
    }

    public static List<Card> createCardListDefault(){
        return List.of(new Card(ID, NAME, MASTERCARD, INCOME, BASIC_LIMIT),
                       new Card(2L, "GOLDEN", CardFlag.VISA, BigDecimal.valueOf(10000), BigDecimal.valueOf(1500)),
                       new Card(3L, "SILVER", CardFlag.ELO, BigDecimal.valueOf(3000), BigDecimal.valueOf(900)));
    }
    
}
