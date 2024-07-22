package com.erickmarques.ms_cartoes.assertions;

import com.erickmarques.ms_cartoes.application.representation.CardSaveResponse;
import com.erickmarques.ms_cartoes.domain.Card;
import static org.assertj.core.api.Assertions.assertThat;

public class CardAssertions {

    public static void assertCardDefault(Card card, CardSaveResponse cardSaveResponse) {
        assertThat(cardSaveResponse.getId()).isEqualTo(card.getId());
        assertThat(cardSaveResponse.getNome()).isEqualTo(card.getName());
        assertThat(cardSaveResponse.getBandeiraCartao()).isEqualTo(card.getCardFlag());
        assertThat(cardSaveResponse.getRenda()).isEqualTo(card.getIncome());
        assertThat(cardSaveResponse.getLimiteBasico()).isEqualTo(card.getBasicLimit());
    }
}
