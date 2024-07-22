package com.erickmarques.ms_clientes.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import com.erickmarques.ms_clientes.application.representation.CustomerSaveRequest;
import com.erickmarques.ms_clientes.application.representation.CustomerSaveResponse;
import com.erickmarques.ms_clientes.factory.CustomerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe de teste para {@link CustomerResource}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do Controlador do Cliente")
public class CustomerResourceTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CustomerService customerService;

    private final String BASE_URL = "/api/clientes";

    @Nested
    @DisplayName("Criar um novo Cliente")
    class TestSaveCustomer {
        
        @Test
        @DisplayName("Com dados válidos.")
        void givenValidCustomerData_whenSaveCustomer_thenReturnSavedCustomer() throws Exception{

            // cenário
            CustomerSaveRequest  customerSaveRequest  = CustomerFactory.createCustomerSaveRequestDefault();
            CustomerSaveResponse customerSaveResponse = CustomerFactory.createCustomerSaveResponseDefault();
            when(customerService.createCustomer(any(CustomerSaveRequest.class)))
                .thenReturn(customerSaveResponse);

            // ação / verificação
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(customerSaveRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andExpect(jsonPath("$.nome").value(customerSaveRequest.getNome()))
                    .andExpect(jsonPath("$.cpf").value(customerSaveRequest.getCpf()))
                    .andExpect(jsonPath("$.idade").value(customerSaveRequest.getIdade()));
        } 

        @Test
        @DisplayName("Com nome nulo.")
        void givenCustomerWithNullName_whenSaveCustomer_thenThrowException() throws Exception{

            // cenário
            CustomerSaveRequest customerWithNameIsNull = CustomerSaveRequest
                                                            .builder()
                                                            .nome(null)
                                                            .cpf(CustomerFactory.CPF)
                                                            .idade(CustomerFactory.AGE)
                                                            .build();
            // ação / verificação
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(customerWithNameIsNull)))
                    .andExpect(status().isBadRequest());
        } 

        @Test
        @DisplayName("Com cpf nulo.")
        void givenCustomerWithNullCpf_whenSaveCustomer_thenThrowException() throws Exception{

            // cenário
            CustomerSaveRequest customerWithCpfIsNull = CustomerSaveRequest
                                                            .builder()
                                                            .nome(CustomerFactory.NAME)
                                                            .cpf(null)
                                                            .idade(CustomerFactory.AGE)
                                                            .build();

            // ação / verificação
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(customerWithCpfIsNull)))
                    .andExpect(status().isBadRequest());
        } 

        @Test
        @DisplayName("Com cpf inválido.")
        void givenCustomerWithInvalidCpf_whenSaveCustomer_thenThrowException() throws Exception{

            // cenário
            CustomerSaveRequest customerWithCpfInvalid = CustomerSaveRequest
                                                            .builder()
                                                            .nome(CustomerFactory.NAME)
                                                            .cpf("INVALID_CPF")
                                                            .idade(CustomerFactory.AGE)
                                                            .build();

            // ação / verificação
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(customerWithCpfInvalid)))
                    .andExpect(status().isBadRequest());
        } 

        @Test
        @DisplayName("Com idade nula.")
        void givenCustomerWithNullAge_whenSaveCustomer_thenThrowException() throws Exception{

            // cenário
            CustomerSaveRequest customerWithAgeIsNull = CustomerSaveRequest
                                                            .builder()
                                                            .nome(CustomerFactory.NAME)
                                                            .cpf(CustomerFactory.CPF)
                                                            .idade(null)
                                                            .build();

            // ação / verificação
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(customerWithAgeIsNull)))
                    .andExpect(status().isBadRequest());
        } 
    }

    @Nested
    @DisplayName("Pesquisar um Cliente pelo cpf.")
    class TestFindBtCpf {

        @Test
        @DisplayName("Com cpf existente.")
        void givenExistingCpf_WhenFindByCpf_ReturnCustomer() throws Exception {
    
            // cenário
            CustomerSaveRequest  customerSaveRequest  = CustomerFactory.createCustomerSaveRequestDefault();
            CustomerSaveResponse customerSaveResponse = CustomerFactory.createCustomerSaveResponseDefault();
            when(customerService.findByCpf(CustomerFactory.CPF)).thenReturn(customerSaveResponse);
    
            // ação / verificação
            mockMvc.perform(get(BASE_URL)
                    .param("cpf", CustomerFactory.CPF))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andExpect(jsonPath("$.nome").value(customerSaveRequest.getNome()))
                    .andExpect(jsonPath("$.cpf").value(customerSaveRequest.getCpf()))
                    .andExpect(jsonPath("$.idade").value(customerSaveRequest.getIdade()));
        }

        @Test
        @DisplayName("Com cpf inexistente.")
        void givenNotExistingCpf_WhenFindByCpf_thenThrowException() throws Exception {
    
            // cenário
            doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado para o ID: " + CustomerFactory.CPF))
                .when(customerService).findByCpf(CustomerFactory.CPF);

            // ação / verificação
            mockMvc.perform(get(BASE_URL)
                    .param("cpf", CustomerFactory.CPF))
                    .andExpect(status().isNotFound());
        }
    }
}
