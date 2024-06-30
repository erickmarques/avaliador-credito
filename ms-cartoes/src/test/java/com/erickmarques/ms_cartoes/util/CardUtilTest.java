package com.erickmarques.ms_cartoes.util;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import com.erickmarques.ms_cartoes.application.representation.CardSaveRequest;
import com.erickmarques.ms_cartoes.application.representation.CardSaveResponse;
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

    public static CardSaveRequest createCardSaveRequestDefault() {
        return new CardSaveRequest(NAME, MASTERCARD.toString(), INCOME, BASIC_LIMIT);
    }

    public static CardSaveResponse createCardSaveResponseDefault() {
        return new CardSaveResponse(ID, NAME, MASTERCARD, INCOME, BASIC_LIMIT);
    }

    public static void assertCostumerDefault(Card card, CardSaveResponse cardSaveResponse) {
        assertThat(cardSaveResponse.getId()).isEqualTo(card.getId());
        assertThat(cardSaveResponse.getNome()).isEqualTo(card.getName());
        assertThat(cardSaveResponse.getBandeiraCartao()).isEqualTo(card.getCardFlag());
        assertThat(cardSaveResponse.getRenda()).isEqualTo(card.getIncome());
        assertThat(cardSaveResponse.getLimiteBasico()).isEqualTo(card.getBasicLimit());
    }
    
}
