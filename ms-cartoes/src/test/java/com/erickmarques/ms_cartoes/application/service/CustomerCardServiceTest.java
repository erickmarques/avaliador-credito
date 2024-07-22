package com.erickmarques.ms_cartoes.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.erickmarques.ms_cartoes.application.mapper.CustomerCardMapper;
import com.erickmarques.ms_cartoes.application.representation.CustomerCardResponse;
import com.erickmarques.ms_cartoes.domain.Card;
import com.erickmarques.ms_cartoes.domain.CardRequestData;
import com.erickmarques.ms_cartoes.domain.CustomerCard;
import com.erickmarques.ms_cartoes.factory.CardFactory;
import com.erickmarques.ms_cartoes.factory.CustomerCardFactory;
import com.erickmarques.ms_cartoes.infra.repository.CustomerCardRepository;

/**
 * Classe de teste para {@link CardService}.
 */
@DisplayName("Testes do Service dos Cartões do Cliente.")
@ExtendWith(MockitoExtension.class)
public class CustomerCardServiceTest {

    @Mock
    private CardService cardService;

    @Mock
    private CustomerCardMapper customerCardMapper;

    @Mock
    private CustomerCardRepository customerCardRepository;

    @InjectMocks
    private CustomerCardService customerCardService;

    private Card card;
    private CustomerCard customerCard;
    private CardRequestData cardRequestData;

    @Nested
    class FindCardsByCpf {

        @Test
        @DisplayName("Teste para retornar cartões de um cliente por cpf.")
        public void givenCustomerCards_WhenfindByCpf_ThenReturnCustomerCards() {

            // cenário
            List<Card> cards = CardFactory.createCardListDefault();
            when(customerCardRepository.findByCpfOrderByCardLimitDesc(anyString())).thenReturn(CustomerCardFactory.createCustomerCardListDefault(cards));
            
            // ação
            List<CustomerCardResponse> customerCards = customerCardService.findCardsByCpf(anyString());

            // verificação
            assertNotNull(customerCards);
            assertThat(customerCards).hasSize(4);
            verify(customerCardRepository, times(1)).findByCpfOrderByCardLimitDesc(anyString());
        }

        @Test
        @DisplayName("Teste para retornar uma lista vazia de cartões de um cliente ao pesquisar por um cpf que não existe.")
        public void givenCustomerCardWithNotExistingCpf_WhenfindByCpf_ThenReturnEmptyList() {

            // cenário
            when(customerCardRepository.findByCpfOrderByCardLimitDesc(anyString())).thenReturn(Collections.emptyList());
            
            // ação
            List<CustomerCardResponse> customerCards = customerCardService.findCardsByCpf(anyString());

            // verificação
            assertThat(customerCards).hasSize(0);
            verify(customerCardRepository, times(1)).findByCpfOrderByCardLimitDesc(anyString());
        }
    }

    @Nested
    class CreateCustomerCard {
        @Test
        @DisplayName("Teste para criar um novo cartão para um cliente.")
        void givenNewCustomerCard_whenSave_thenCustomerCardIsCreatedSuccessfully(){
            
            // cenário
            card            = CardFactory.createCardDefault();
            customerCard    = CustomerCardFactory.createCustomerCardDefault();
            cardRequestData = CustomerCardFactory.createCardRequestDataDefault();

            when(cardService.findById(anyLong())).thenReturn(card);
            when(customerCardMapper.toEntity(cardRequestData, card)).thenReturn(customerCard);

            // ação
            customerCardService.save(cardRequestData);

            // verificação
            verify(customerCardRepository, times(1)).save(customerCard);
        }
    }
    
}
