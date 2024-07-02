package com.erickmarques.ms_cartoes.application.resource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.erickmarques.ms_cartoes.application.service.CustomerCardService;
import com.erickmarques.ms_cartoes.util.CardUtilTest;
import com.erickmarques.ms_cartoes.util.CustomerCardUtilTest;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    

    @Test
    void whenCpfProvided_thenReturnListOfCustomerCards() throws Exception{

        when(customerCardService.findCardsByCpf(CustomerCardUtilTest.CPF))
            .thenReturn(CustomerCardUtilTest.createCustomerCardResponseListDefault(List.of(CardUtilTest.createCardSaveResponseDefault())));

        mockMvc.perform(get(BASE_URL)
                    .param("cpf", CustomerCardUtilTest.CPF))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").isNotEmpty());
    }
}
