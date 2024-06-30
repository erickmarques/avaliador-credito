package com.erickmarques.ms_cartoes.application.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.erickmarques.ms_cartoes.application.representation.CardSaveRequest;
import com.erickmarques.ms_cartoes.application.representation.CardSaveResponse;
import com.erickmarques.ms_cartoes.domain.Card;
import com.erickmarques.ms_cartoes.util.CardUtilTest;

/**
 * Classe de teste para {@link CardMapper}.
 */
@DisplayName("Testes do Mapper de Cartão")
@SpringBootTest
public class CardMapperTest {

    @Autowired
    private CardMapper cardMapper;

    @Test
    public void givenCardSaveRequest_whenMappingToEntity_thenMapCorrectly() {

        // cenário
        CardSaveRequest cardSaveRequest  = CardUtilTest.createCardSaveRequestDefault();

        // ação
        Card card = cardMapper.toEntity(cardSaveRequest);

        // verificação
        assertThat(cardSaveRequest.getName()).isEqualTo(card.getName());
        assertThat(cardSaveRequest.getCardFlag()).isEqualTo(card.getCardFlag());
        assertThat(cardSaveRequest.getIncome()).isEqualTo(card.getIncome());
        assertThat(cardSaveRequest.getBasicLimit()).isEqualTo(card.getBasicLimit());
    }

    @Test
    public void givenCard_whenMappingToDto_thenMapCorrectly() {

        // cenário
        Card card = CardUtilTest.createCardDefault();

        // ação
        CardSaveResponse cardSaveResponse = cardMapper.toDto(card);

         // verificação
         CardUtilTest.assertCostumerDefault(card, cardSaveResponse);
    }
    
}

