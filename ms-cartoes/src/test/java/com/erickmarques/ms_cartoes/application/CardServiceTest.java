package com.erickmarques.ms_cartoes.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.erickmarques.ms_cartoes.application.mapper.CardMapper;
import com.erickmarques.ms_cartoes.application.representation.CardSaveRequest;
import com.erickmarques.ms_cartoes.application.representation.CardSaveResponse;
import com.erickmarques.ms_cartoes.domain.Card;
import com.erickmarques.ms_cartoes.infra.CardRepository;
import com.erickmarques.ms_cartoes.util.CardUtilTest;

/**
 * Classe de teste para {@link CardService}.
 */
@DisplayName("Testes do Service de Cartão")
@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardMapper cardMapper;

    @InjectMocks
    private CardService cardService;

    private Card card;
    private CardSaveRequest cardSaveRequest;
    private CardSaveResponse cardSaveResponse;

    @BeforeEach
    void setUp() {
        card             = CardUtilTest.createCardDefault();
        cardSaveRequest  = CardUtilTest.createCardSaveRequestDefault();
        cardSaveResponse = CardUtilTest.createCardSaveResponseDefault();
    }

    @Nested
    class CreateCard {

        @Test
        @DisplayName("Teste para criar um cartão com dados válidos.")
        void givenNewCard_whenCreateCard_thenCardIsCreatedSuccessfully(){
            
            // cenário
            when(cardMapper.toEntity(any(CardSaveRequest.class))).thenReturn(card);
            when(cardRepository.save(any(Card.class))).thenReturn(card);
            when(cardMapper.toDto(any(Card.class))).thenReturn(cardSaveResponse);

            // ação
            CardSaveResponse cardSaveResponse = cardService.createCard(cardSaveRequest);

            // verificação
            verify(cardRepository, times(1)).save(any(Card.class));
            CardUtilTest.assertCostumerDefault(card, cardSaveResponse);
        }
    }
    
}
