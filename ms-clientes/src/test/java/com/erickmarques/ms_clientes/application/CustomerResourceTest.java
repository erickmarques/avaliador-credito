package com.erickmarques.ms_clientes.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.jupiter.api.BeforeEach;
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
import com.erickmarques.ms_clientes.util.CustomerUtilTest;
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

    private CustomerSaveRequest customerSaveRequest;
    private CustomerSaveResponse customerSaveResponse;

    @BeforeEach
    void setUp() {
        customerSaveRequest  = CustomerUtilTest.createCustomerSaveRequestDefault();
        customerSaveResponse = CustomerUtilTest.createCustomerSaveResponseDefault();
    }

    @Nested
    @DisplayName("Criar um novo Cliente")
    class TestSaveCustomer {
        
        @Test
        @DisplayName("Com dados válidos.")
        void givenValidCustomerData_whenSaveCustomer_thenReturnSavedCustomer() throws Exception{

            when(customerService.createCustomer(any(CustomerSaveRequest.class)))
                .thenReturn(customerSaveResponse);

            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(customerSaveRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andExpect(jsonPath("$.name").value(customerSaveRequest.getNome()))
                    .andExpect(jsonPath("$.cpf").value(customerSaveRequest.getCpf()))
                    .andExpect(jsonPath("$.age").value(customerSaveRequest.getIdade()));
        } 

        @Test
        @DisplayName("Com nome nulo.")
        void givenCustomerWithNullName_whenSaveCustomer_thenThrowException() throws Exception{

            customerSaveRequest.setNome(null);

            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(customerSaveRequest)))
                    .andExpect(status().isBadRequest());
        } 

        @Test
        @DisplayName("Com cpf nulo.")
        void givenCustomerWithNullCpf_whenSaveCustomer_thenThrowException() throws Exception{

            customerSaveRequest.setCpf(null);

            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(customerSaveRequest)))
                    .andExpect(status().isBadRequest());
        } 

        @Test
        @DisplayName("Com cpf inválido.")
        void givenCustomerWithInvalidCpf_whenSaveCustomer_thenThrowException() throws Exception{

            customerSaveRequest.setCpf("INVALID_CPF");

            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(customerSaveRequest)))
                    .andExpect(status().isBadRequest());
        } 

        @Test
        @DisplayName("Com idade nula.")
        void givenCustomerWithNullAge_whenSaveCustomer_thenThrowException() throws Exception{

            customerSaveRequest.setIdade(null);

            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(customerSaveRequest)))
                    .andExpect(status().isBadRequest());
        } 
    }

    @Nested
    @DisplayName("Pesquisar um Cliente pelo cpf.")
    class TestFindBtCpf {

        @Test
        @DisplayName("Com cpf existente.")
        void givenExistingCpf_WhenFindByCpf_ReturnCustomer() throws Exception {
    
            when(customerService.findByCpf(CustomerUtilTest.CPF)).thenReturn(customerSaveResponse);
    
            mockMvc.perform(get(BASE_URL)
                    .param("cpf", CustomerUtilTest.CPF))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andExpect(jsonPath("$.name").value(customerSaveRequest.getNome()))
                    .andExpect(jsonPath("$.cpf").value(customerSaveRequest.getCpf()))
                    .andExpect(jsonPath("$.age").value(customerSaveRequest.getIdade()));
        }

        @Test
        @DisplayName("Com cpf inexistente.")
        void givenNotExistingCpf_WhenFindByCpf_thenThrowException() throws Exception {
    
            doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado para o ID: " + CustomerUtilTest.CPF))
                .when(customerService).findByCpf(CustomerUtilTest.CPF);

            mockMvc.perform(get(BASE_URL)
                    .param("cpf", CustomerUtilTest.CPF))
                    .andExpect(status().isNotFound());
        }
    }
}
