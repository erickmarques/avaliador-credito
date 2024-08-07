package com.erickmarques.ms_cartoes.application.resource;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Collections;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.erickmarques.ms_cartoes.application.representation.CardSaveResponse;
import com.erickmarques.ms_cartoes.application.representation.CustomerCardResponse;
import com.erickmarques.ms_cartoes.application.service.CustomerCardService;
import com.erickmarques.ms_cartoes.factory.CardFactory;
import com.erickmarques.ms_cartoes.factory.CustomerCardFactory;

import static org.hamcrest.Matchers.hasSize;

/**
 * Classe de teste para {@link CustomerCardResource}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do Controlador do Cartões do Cliente")
public class CustomerCardResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerCardService customerCardService;

    private final String BASE_URL = "/api/cliente-cartoes";

    @Nested
    class FindCardsByCpf {

        @Test
        @DisplayName("Teste de pesquisar por cpf.")
        void givenCustomerCards_whenCpfProvided_thenReturnListOfCustomerCards() throws Exception{
    
            // cenário
            List<CardSaveResponse> cards = List.of(CardFactory.createCardSaveResponseDefault());
            List<CustomerCardResponse> customerCards = CustomerCardFactory.createCustomerCardResponseListDefault(cards);
            when(customerCardService.findCardsByCpf(CustomerCardFactory.CPF)).thenReturn(customerCards);
    
            // ação / verificação
            mockMvc.perform(get(BASE_URL)
                        .param("cpf", CustomerCardFactory.CPF))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].id").value(customerCards.get(0).getId()))
                        .andExpect(jsonPath("$[0].cpf").value(customerCards.get(0).getCpf()))
                        .andExpect(jsonPath("$[0].limiteCartao").value(customerCards.get(0).getLimiteCartao()))
                        .andExpect(jsonPath("$[0].cartoes").isNotEmpty());
        }
    
        @Test
        @DisplayName("Teste de pesquisar por um cpf inexistente.")
        void givenCustomerCardWithNotExistingCpf_WhenfindByCpf_ThenReturnEmptyList() throws Exception{
    
            // cenário
            when(customerCardService.findCardsByCpf(CustomerCardFactory.CPF)).thenReturn(Collections.emptyList());
    
            // ação / verificação
            mockMvc.perform(get(BASE_URL)
                        .param("cpf", CustomerCardFactory.CPF))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(0)));
        }
    }
}
