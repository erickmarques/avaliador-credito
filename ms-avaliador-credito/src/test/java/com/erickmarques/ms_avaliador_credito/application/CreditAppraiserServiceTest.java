package com.erickmarques.ms_avaliador_credito.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.erickmarques.ms_avaliador_credito.domain.CardRequestData;
import com.erickmarques.ms_avaliador_credito.domain.CardRequestProtocol;
import com.erickmarques.ms_avaliador_credito.domain.EvaluationData;
import com.erickmarques.ms_avaliador_credito.domain.response.CardResponse;
import com.erickmarques.ms_avaliador_credito.domain.response.CustomerCardResponse;
import com.erickmarques.ms_avaliador_credito.domain.response.CustomerResponse;
import com.erickmarques.ms_avaliador_credito.domain.response.CustomerSituation;
import com.erickmarques.ms_avaliador_credito.domain.response.EvaluationReturn;
import com.erickmarques.ms_avaliador_credito.infra.clients.CardResourceClient;
import com.erickmarques.ms_avaliador_credito.infra.clients.CustomerResourceClient;
import com.erickmarques.ms_avaliador_credito.infra.mqueue.CardIssuanceRequestPublisher;
import com.erickmarques.ms_avaliador_credito.util.CreditAppraiserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;

/**
 * Classe de teste para {@link CreditAppraiserService}.
 */

@ExtendWith(MockitoExtension.class)
public class CreditAppraiserServiceTest {

    @Mock
    private CardResourceClient cardResourceClient;

    @Mock
    private CustomerResourceClient customerResourceClient;

    @Mock
    private CardIssuanceRequestPublisher cardIssuanceRequestPublisher;;

    @InjectMocks
    private CreditAppraiserService creditAppraiserService;

	private CustomerResponse customerResponse;
    private CardRequestData cardRequestData;

    @BeforeEach
    void setUp() {
        customerResponse = CustomerResponse
                                .builder()
                                .id(CreditAppraiserUtil.ID)
                                .cpf(CreditAppraiserUtil.CPF)
                                .nome(CreditAppraiserUtil.NAME)
                                .idade(CreditAppraiserUtil.AGE)
                                .build();

        cardRequestData = CardRequestData
                            .builder()
                            .idCard(CreditAppraiserUtil.ID)
                            .cpf(CreditAppraiserUtil.CPF)
                            .address("Rua da Lira, Recife")
                            .limitReleased(CreditAppraiserUtil.LIMIT)
                            .build();
    }

    @Test
    void shouldReturnCustomerSituation_WhenClientDataIsFound() {
        // cenário
        List<CustomerCardResponse> customerCards    = List.of(CustomerCardResponse
                                                            .builder()
                                                            .id(CreditAppraiserUtil.ID)
                                                            .cpf(CreditAppraiserUtil.CPF)
                                                            .limiteCartao(BigDecimal.TEN)
                                                            .build()
                                                        );

        when(customerResourceClient.getCustomerData(CreditAppraiserUtil.CPF)).thenReturn(new ResponseEntity<>(customerResponse, HttpStatus.OK));
        when(cardResourceClient.getCardsByCpf(CreditAppraiserUtil.CPF)).thenReturn(new ResponseEntity<>(customerCards, HttpStatus.OK));

        // ação
        CustomerSituation situation = creditAppraiserService.getSituation(CreditAppraiserUtil.CPF);

        // verificação
        assertThat(situation.getCliente()).isEqualTo(customerResponse);
        assertThat(situation.getCartoes()).isEqualTo(customerCards);
    }

    @Test
    public void shouldReturnApprovedCards_WhenEvaluationDataIsValid() {
        // cenário
        EvaluationData evaluationData = EvaluationData
                                            .builder()
                                            .cpf(CreditAppraiserUtil.CPF)
                                            .income(CreditAppraiserUtil.INCOME)
                                            .build();

        List<CardResponse> cards = List.of(CardResponse
                                            .builder()
                                            .id(CreditAppraiserUtil.ID)
                                            .nome(CreditAppraiserUtil.CARD_NAME)
                                            .bandeiraCartao(CreditAppraiserUtil.CARD_FLAG)
                                            .limiteBasico(CreditAppraiserUtil.LIMIT)
                                            .renda(CreditAppraiserUtil.INCOME)
                                            .build()
                                    );

        when(customerResourceClient.getCustomerData(CreditAppraiserUtil.CPF)).thenReturn(new ResponseEntity<>(customerResponse, HttpStatus.OK));
        when(cardResourceClient.getCardsWithIncomeUpTo(CreditAppraiserUtil.INCOME.longValue())).thenReturn(new ResponseEntity<>(cards, HttpStatus.OK));

        // ação
        EvaluationReturn evaluationReturn = creditAppraiserService.realizeEvaluation(evaluationData);

        // verificação
        assertNotNull(evaluationReturn);
        assertEquals(1, evaluationReturn.getCartoes().size());
        assertEquals(CreditAppraiserUtil.CARD_NAME, evaluationReturn.getCartoes().get(0).getCard());
        assertEquals(CreditAppraiserUtil.CARD_FLAG, evaluationReturn.getCartoes().get(0).getFlag());
    }

    @Test
    public void shouldReturnProtocol_WhenRequestCardIssuanceIsSuccessful() throws JsonProcessingException {
        // cenário
        doNothing().when(cardIssuanceRequestPublisher).requestCard(cardRequestData);

        // ação
        CardRequestProtocol cardRequestProtocol = creditAppraiserService.requestCardIssuance(cardRequestData);

        // verificação
        assertNotNull(cardRequestProtocol);
        assertNotNull(cardRequestProtocol.getProtocol());
        assertTrue(UUID.fromString(cardRequestProtocol.getProtocol()) != null); // Verifica se é um UUID válido
    }

    @Test
    public void shouldThrowResponseStatusExceptionWhenJsonProcessingExceptionIsThrown() throws JsonProcessingException {
        
        // cenário
        doThrow(new JsonProcessingException("Error") {}).when(cardIssuanceRequestPublisher).requestCard(cardRequestData);

        // ação
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            creditAppraiserService.requestCardIssuance(cardRequestData);
        });

        // verificação
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Ocorreu um erro ao realizar a solicitação do cartão!", exception.getReason());
    }

    
}
