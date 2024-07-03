package com.erickmarques.ms_cartoes.application.mapper;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
        assertThat(cardSaveRequest.getNome()).isEqualTo(card.getName());
        assertThat(cardSaveRequest.getBandeiraCartao()).isEqualTo(card.getCardFlag().toString());
        assertThat(cardSaveRequest.getRenda()).isEqualTo(card.getIncome());
        assertThat(cardSaveRequest.getLimiteBasico()).isEqualTo(card.getBasicLimit());
    }

    @Test
    public void givenCardSaveRequest_whenMappingToEntityWithCardFlagInvalid_thenReturnException() {

        // cenário
        CardSaveRequest cardSaveRequest  = CardSaveRequest
                                            .builder()
                                            .nome(CardUtilTest.NAME)
                                            .bandeiraCartao("INVALID_CARDFLAG")
                                            .renda(CardUtilTest.INCOME)
                                            .limiteBasico(CardUtilTest.BASIC_LIMIT)
                                            .build();

        // ação
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, 
                () -> cardMapper.toEntity(cardSaveRequest));

        // verificação
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo("Favor informar uma bandeira de cartão válida!");
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

