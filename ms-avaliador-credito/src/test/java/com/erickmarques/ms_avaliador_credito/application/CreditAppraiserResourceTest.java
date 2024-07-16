package com.erickmarques.ms_avaliador_credito.application;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.erickmarques.ms_avaliador_credito.domain.CardRequestData;
import com.erickmarques.ms_avaliador_credito.domain.CardRequestProtocol;
import com.erickmarques.ms_avaliador_credito.domain.EvaluationData;
import com.erickmarques.ms_avaliador_credito.domain.response.CustomerCardResponse;
import com.erickmarques.ms_avaliador_credito.domain.response.CustomerResponse;
import com.erickmarques.ms_avaliador_credito.domain.response.CustomerSituation;
import com.erickmarques.ms_avaliador_credito.domain.response.EvaluationReturn;
import com.erickmarques.ms_avaliador_credito.util.CreditAppraiserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

/**
 * Classe de teste para {@link CreditAppraiserResource}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do Controlador do Avaliador de Crédito")
public class CreditAppraiserResourceTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreditAppraiserService creditAppraiserService;

    private final String BASE_URL = "/api/avaliacoes-credito";

    @Test
    @DisplayName("Com cpf existente.")
    void givenExistingCpf_WhenConsultarSituacaoCliente_ReturnCustomerWithCards() throws Exception {

        // cenário
        CustomerResponse cliente = CustomerResponse.builder().build();
        List<CustomerCardResponse> cartoes  = List.of(CustomerCardResponse.builder().build()) ;                                    
        CustomerSituation customerSituation = CustomerSituation
                                                .builder()
                                                .cliente(cliente)
                                                .cartoes(cartoes)
                                                .build();

        when(creditAppraiserService.getSituation(CreditAppraiserUtil.CPF)).thenReturn(customerSituation);

        // ação / verificação
            mockMvc.perform(get(BASE_URL.concat("/situacao-cliente"))
                    .param("cpf", CreditAppraiserUtil.CPF))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.cliente").isNotEmpty())
                    .andExpect(jsonPath("$.cartoes").isArray());
    }

    @Test
    public void shouldReturnEvaluationWhenRealizarAvaliacaoIsCalled() throws Exception {
        // cenário
        EvaluationData evaluationData = EvaluationData.builder().cpf(CreditAppraiserUtil.CPF).income(CreditAppraiserUtil.INCOME).build();
        EvaluationReturn expectedReturn = EvaluationReturn.builder().build();;
        when(creditAppraiserService.realizeEvaluation(evaluationData)).thenReturn(expectedReturn);

        // ação / verificação
        mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(evaluationData)))
                    .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnBadRequestWhenCpfIsInvalid() throws Exception {
        // cenário
        EvaluationData evaluationData = EvaluationData.builder().cpf("CPF_INVALID").income(CreditAppraiserUtil.INCOME).build();

        // ação / verificação
        mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(evaluationData)))
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenCpfIsEmpty() throws Exception {
        // cenário
        EvaluationData evaluationData = EvaluationData.builder().cpf(" ").income(CreditAppraiserUtil.INCOME).build();

        // ação / verificação
        mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(evaluationData)))
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenIncomeIsNull() throws Exception {
        // cenário
        EvaluationData evaluationData = EvaluationData.builder().cpf(CreditAppraiserUtil.CPF).income(null).build();

        // ação / verificação
        mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(evaluationData)))
                    .andExpect(status().isBadRequest());
    }

    @Test
    void shouldSolicitarCartaoSuccessfully() throws Exception {

        // cenário
        CardRequestData cardRequestData = CardRequestData
                                            .builder()
                                            .idCard(CreditAppraiserUtil.ID)
                                            .cpf(CreditAppraiserUtil.CPF)
                                            .address("Rua da Lira, Recife")
                                            .limitReleased(CreditAppraiserUtil.LIMIT)
                                            .build();
        CardRequestProtocol cardRequestProtocol = CardRequestProtocol.builder().build();
        when(creditAppraiserService.requestCardIssuance(cardRequestData)).thenReturn(cardRequestProtocol);

        // ação / verificação
        mockMvc.perform(post(BASE_URL.concat("/solicitacoes-cartao"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(cardRequestData)))
                    .andExpect(status().isOk());
    }
}
