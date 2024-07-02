package com.erickmarques.ms_cartoes.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.erickmarques.ms_cartoes.application.representation.CardSaveRequest;
import com.erickmarques.ms_cartoes.domain.CardRequestData;
import com.erickmarques.ms_cartoes.util.CustomerCardUtilTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe de teste para {@link CardIssuanceService}.
 */
@DisplayName("Testes do Service de Solicitação de Cartão")
@ExtendWith(MockitoExtension.class)
public class CardIssuanceServiceTest {

    @Mock
    private CustomerCardService customerCardService;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private CardIssuanceService cardIssuanceService;

    @Test
    void whenValidPayload_thenSaveCardRequestData() throws JsonMappingException, JsonProcessingException{
        
        // cenário
        CardRequestData data = CustomerCardUtilTest.createCardRequestDataDefault();
        String payload       = mapper.writeValueAsString(data);
        when(mapper.readValue(payload, CardRequestData.class)).thenReturn(data);

        // ação
        cardIssuanceService.processIssuanceRequest(payload);

        // verificação
        verify(customerCardService, times(1)).save(data);
        verify(mapper, times(1)).readValue(payload, CardRequestData.class);
    }
    
}
