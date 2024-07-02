package com.erickmarques.ms_cartoes.application.resource;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.erickmarques.ms_cartoes.application.representation.CardSaveRequest;
import com.erickmarques.ms_cartoes.application.representation.CardSaveResponse;
import com.erickmarques.ms_cartoes.application.service.CardService;
import com.erickmarques.ms_cartoes.util.CardUtilTest;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.hasSize;
import java.util.Collections;

/**
 * Classe de teste para {@link CardResource}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do Controlador do Cartões.")
public class CardResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CardService cardService;

    private final String BASE_URL = "/api/cartoes";

    private CardSaveRequest cardSaveRequest;
    private CardSaveResponse cardSaveResponse;

    @BeforeEach
    void setUp() {
        // cenário
        cardSaveRequest  = CardUtilTest.createCardSaveRequestDefault();
        cardSaveResponse = CardUtilTest.createCardSaveResponseDefault();
    }

    @Nested
    class CriarCartao {

        @Test
        @DisplayName("Teste para criar um cartão com dados válidos.")
        void givenNewCard_whenCreateCard_thenCardIsCreatedSuccessfully() throws Exception{

            // cenário
            when(cardService.createCard(cardSaveRequest)).thenReturn(cardSaveResponse);

            // ação / verificação
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(cardSaveRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").isNotEmpty());
        }

        @Test
        @DisplayName("Teste para levantar uma exceção ao criar cartão sem nome.")
        void givenNewCard_whenNameIsNull_thenReturnBadRequest() throws Exception{

            // cenário
            cardSaveRequest.setNome(null);

            // ação / verificação
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(cardSaveRequest)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Teste para levantar uma exceção ao criar cartão com nome vazio.")
        void givenNewCard_whenNameIsEmpty_thenReturnBadRequest() throws Exception{

            // cenário
            cardSaveRequest.setNome(" ");

            // ação / verificação
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(cardSaveRequest)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Teste para levantar uma exceção ao criar cartão sem a bandeira.")
        void givenNewCard_whenCardFlagIsNull_thenReturnBadRequest() throws Exception{

            // cenário
            cardSaveRequest.setBandeiraCartao(null);

            // ação / verificação
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(cardSaveRequest)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Teste para levantar uma exceção ao criar cartão com a bandeira vazia.")
        void givenNewCard_whenCardFlagIsEmpty_thenReturnBadRequest() throws Exception{

            // cenário
            cardSaveRequest.setBandeiraCartao(" ");

            // ação / verificação
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(cardSaveRequest)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Teste para levantar uma exceção ao criar cartão sem a renda.")
        void givenNewCard_whenCardIncomeIsNull_thenReturnBadRequest() throws Exception{

            // cenário
            
            cardSaveRequest.setRenda(null);

            // ação / verificação
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(cardSaveRequest)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Teste para levantar uma exceção ao criar cartão sem o limite básico.")
        void givenNewCard_whenCardBasicLimitIsNull_thenReturnBadRequest() throws Exception{

            // cenário
            
            cardSaveRequest.setLimiteBasico(null);

            // ação / verificação
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(cardSaveRequest)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class PesquisarCartoesComRendaAte {

        @Test
        @DisplayName("Teste de pesquisar por cpf.")
        void givenCards_WhenIncomeLessThanOrEqual_ThenReturnCards() throws Exception{

            //cenário
            Long renda = 3000L;
            when(cardService.findByIncomeLessThanEqualOrderByIncomeDesc(renda))
                .thenReturn(CardUtilTest.createCardSaveResponseListDefault());
    
            // ação / verificação
            mockMvc.perform(get(BASE_URL)
                        .param("renda", String.valueOf(renda)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(3)));
        }

        @Test
        @DisplayName("Teste para retornar uma lista vazia de cartões.")
        void givenCards_WhenIncomeLessThanOrEqual_ThenReturnEmptyList() throws Exception{

            //cenário
            Long renda = 3000L;
            when(cardService.findByIncomeLessThanEqualOrderByIncomeDesc(renda))
                .thenReturn(Collections.emptyList());
    
            // ação / verificação
            mockMvc.perform(get(BASE_URL)
                        .param("renda", String.valueOf(renda)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(0)));
        }

    }
    
}