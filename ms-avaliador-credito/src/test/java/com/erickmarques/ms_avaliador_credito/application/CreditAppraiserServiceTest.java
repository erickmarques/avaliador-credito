package com.erickmarques.ms_avaliador_credito.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.erickmarques.ms_avaliador_credito.domain.EvaluationData;
import com.erickmarques.ms_avaliador_credito.domain.response.CardResponse;
import com.erickmarques.ms_avaliador_credito.domain.response.CustomerCardResponse;
import com.erickmarques.ms_avaliador_credito.domain.response.CustomerResponse;
import com.erickmarques.ms_avaliador_credito.domain.response.CustomerSituation;
import com.erickmarques.ms_avaliador_credito.domain.response.EvaluationReturn;
import com.erickmarques.ms_avaliador_credito.infra.clients.CardResourceClient;
import com.erickmarques.ms_avaliador_credito.infra.clients.CustomerResourceClient;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    private final Long ID           = 1L;
    private final String CPF        = "12345678900";
    private final String NAME       = "ERICK MARQUES";
    private final String CARD_NAME  = "CARTÃO OURO";
    private final String CARD_FLAG  = "MASTERCARD";
    private final Integer AGE       = 30;
    private final BigDecimal INCOME = BigDecimal.valueOf(3000);
    private final BigDecimal LIMIT  = BigDecimal.valueOf(300);
	
	private CustomerResponse customerResponse;

    @BeforeEach
    void setUp() {
        customerResponse = CustomerResponse
                                .builder()
                                .id(ID)
                                .cpf(CPF)
                                .nome(NAME)
                                .idade(AGE)
                                .build();
    }

    @Test
    void shouldReturnCustomerSituation_WhenClientDataIsFound() {
        // cenário
        List<CustomerCardResponse> customerCards    = List.of(CustomerCardResponse
                                                            .builder()
                                                            .id(ID)
                                                            .cpf(CPF)
                                                            .limiteCartao(BigDecimal.TEN)
                                                            .build()
                                                        );

        when(customerResourceClient.getCustomerData(CPF)).thenReturn(new ResponseEntity<>(customerResponse, HttpStatus.OK));
        when(cardResourceClient.getCardsByCpf(CPF)).thenReturn(new ResponseEntity<>(customerCards, HttpStatus.OK));

        // ação
        CustomerSituation situation = creditAppraiserService.getSituation(CPF);

        // verificação
        assertThat(situation.getCliente()).isEqualTo(customerResponse);
        assertThat(situation.getCartoes()).isEqualTo(customerCards);
    }

    @Test
    public void shouldReturnApprovedCards_WhenEvaluationDataIsValid() {
        // Arrange
        EvaluationData evaluationData = EvaluationData
                                            .builder()
                                            .cpf(CPF)
                                            .income(INCOME)
                                            .build();

        List<CardResponse> cards = List.of(CardResponse
                                            .builder()
                                            .id(ID)
                                            .nome(CARD_NAME)
                                            .bandeiraCartao(CARD_FLAG)
                                            .limiteBasico(LIMIT)
                                            .renda(INCOME)
                                            .build()
                                    );

        when(customerResourceClient.getCustomerData(CPF)).thenReturn(new ResponseEntity<>(customerResponse, HttpStatus.OK));
        when(cardResourceClient.getCardsWithIncomeUpTo(INCOME.longValue())).thenReturn(new ResponseEntity<>(cards, HttpStatus.OK));

        // Act
        EvaluationReturn evaluationReturn = creditAppraiserService.realizeEvaluation(evaluationData);

        // Assert
        assertNotNull(evaluationReturn);
        assertEquals(1, evaluationReturn.getCartoes().size());
        assertEquals(CARD_NAME, evaluationReturn.getCartoes().get(0).getCard());
        assertEquals(CARD_FLAG, evaluationReturn.getCartoes().get(0).getFlag());
    }

    
}
