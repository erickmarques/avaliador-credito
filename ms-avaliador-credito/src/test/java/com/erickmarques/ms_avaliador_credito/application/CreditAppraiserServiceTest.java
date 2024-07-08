package com.erickmarques.ms_avaliador_credito.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.erickmarques.ms_avaliador_credito.domain.response.CustomerCardResponse;
import com.erickmarques.ms_avaliador_credito.domain.response.CustomerResponse;
import com.erickmarques.ms_avaliador_credito.domain.response.CustomerSituation;
import com.erickmarques.ms_avaliador_credito.infra.clients.CardResourceClient;
import com.erickmarques.ms_avaliador_credito.infra.clients.CustomerResourceClient;
import static org.assertj.core.api.Assertions.assertThat;


import static org.mockito.Mockito.when;

import java.util.List;
import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class CreditAppraiserServiceTest {

    @Mock
    private CardResourceClient cardResourceClient;

    @Mock
    private CustomerResourceClient customerResourceClient;

    @InjectMocks
    private CreditAppraiserService creditAppraiserService;

    private final Long ID     = 1L;
    private final String CPF  = "12345678900";
    private final String NAME = "ERICK MARQUES";
    private final Integer AGE = 30;

    private CustomerResponse customerResponse;
    private List<CustomerCardResponse> customerCards;

    @BeforeEach
    void setUp() {
        customerResponse = CustomerResponse
                                .builder()
                                .id(ID)
                                .cpf(CPF)
                                .nome(NAME)
                                .idade(AGE)
                                .build();
                                
        customerCards    = List.of(CustomerCardResponse
                                    .builder()
                                    .id(ID)
                                    .cpf(CPF)
                                    .limiteCartao(BigDecimal.TEN)
                                    .build()
                                );
    }

    @Test
    void shouldReturnCustomerSituation_WhenClientDataIsFound() {
        // cenário
        when(customerResourceClient.getCustomerData(CPF)).thenReturn(new ResponseEntity<>(customerResponse, HttpStatus.OK));
        when(cardResourceClient.getCardsByCpf(CPF)).thenReturn(new ResponseEntity<>(customerCards, HttpStatus.OK));

        // ação
        CustomerSituation situation = creditAppraiserService.getSituation(CPF);

        // verificação
        assertThat(situation.getCliente()).isEqualTo(customerResponse);
        assertThat(situation.getCartoes()).isEqualTo(customerCards);
    }

    
}
