package com.erickmarques.ms_cartoes.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.erickmarques.ms_cartoes.application.mapper.CustomerCardMapper;
import com.erickmarques.ms_cartoes.application.representation.CardSaveRequest;
import com.erickmarques.ms_cartoes.application.representation.CardSaveResponse;
import com.erickmarques.ms_cartoes.domain.Card;
import com.erickmarques.ms_cartoes.domain.CardRequestData;
import com.erickmarques.ms_cartoes.domain.CustomerCard;
import com.erickmarques.ms_cartoes.infra.repository.CustomerCardRepository;
import com.erickmarques.ms_cartoes.util.CardUtilTest;
import com.erickmarques.ms_cartoes.util.CustomerCardUtilTest;

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
    class CreateCard {

        @Test
        @DisplayName("Teste para criar um novo cartão para um cliente.")
        void givenNewCustomerCard_whenSave_thenCustomerCardIsCreatedSuccessfully(){
            
            // cenário
            card            = CardUtilTest.createCardDefault();
            customerCard    = CustomerCardUtilTest.createCustomerCardDefault();
            cardRequestData = CustomerCardUtilTest.createCardRequestDataDefault();

            when(cardService.findById(anyLong())).thenReturn(card);
            when(customerCardMapper.toEntity(cardRequestData, card)).thenReturn(customerCard);

            // ação
            customerCardService.save(cardRequestData);

            // verificação
            verify(customerCardRepository, times(1)).save(customerCard);
        }
    }
    
}
