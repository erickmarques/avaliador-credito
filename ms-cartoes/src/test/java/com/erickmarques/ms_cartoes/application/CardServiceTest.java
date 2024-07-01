package com.erickmarques.ms_cartoes.application;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.erickmarques.ms_cartoes.application.mapper.CardMapper;
import com.erickmarques.ms_cartoes.application.representation.CardSaveRequest;
import com.erickmarques.ms_cartoes.application.representation.CardSaveResponse;
import com.erickmarques.ms_cartoes.domain.Card;
import com.erickmarques.ms_cartoes.infra.repository.CardRepository;
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

        @Test
        @DisplayName("Teste para criar um cartão com nome já existente.")
        void givenCardWithExistingCpf_whenCreateCard_thenResponseStatusException(){
            
            // cenário
            when(cardRepository.findByName(anyString())).thenReturn(Optional.of(new Card()));

            // ação
            ResponseStatusException exception = assertThrows(ResponseStatusException.class, 
                () -> cardService.createCard(cardSaveRequest));

            // verificação
            assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
            assertThat(exception.getReason()).isEqualTo("Já existe um cartão cadastrado com este nome!");
            verify(cardRepository).findByName(anyString());
            verify(cardRepository, never()).save(any(Card.class));
        }
    }

    @Nested
    class FindByIncome {

        @Test
        @DisplayName("Teste para retornar cartões com renda menor ou igual ao valor especificado, ordenados por renda decrescente")
        public void givenCards_WhenIncomeLessThanOrEqual_ThenReturnCards() {

            // cenário
            BigDecimal income = BigDecimal.valueOf(6000);
            when(cardRepository.findByIncomeLessThanEqualOrderByIncomeDesc(income)).thenReturn(CardUtilTest.createCardListDefault());
            
            // ação
            List<CardSaveResponse> cards = cardService.findByIncomeLessThanEqualOrderByIncomeDesc(income.longValue());

            // verificação
            assertNotNull(cards);
            assertThat(cards).hasSize(3);
            verify(cardRepository, times(1)).findByIncomeLessThanEqualOrderByIncomeDesc(income);
        }

        @Test
        @DisplayName("Teste para retornar uma lista vazia de cartões.")
        public void givenCards_WhenIncomeLessThanOrEqual_ThenReturnEmptyList() {

            // cenário
            BigDecimal income = BigDecimal.valueOf(6000);
            when(cardRepository.findByIncomeLessThanEqualOrderByIncomeDesc(income)).thenReturn(Collections.emptyList());
            
            // ação
            List<CardSaveResponse> cards = cardService.findByIncomeLessThanEqualOrderByIncomeDesc(income.longValue());

            // verificação
            assertThat(cards).hasSize(0);
            verify(cardRepository, times(1)).findByIncomeLessThanEqualOrderByIncomeDesc(income);
        }
    }
    
}
