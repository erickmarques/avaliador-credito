package com.erickmarques.ms_cartoes.application.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.erickmarques.ms_cartoes.application.representation.CustomerCardResponse;
import com.erickmarques.ms_cartoes.domain.CustomerCard;
import com.erickmarques.ms_cartoes.util.CustomerCardUtilTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Classe de teste para {@link CustomerCardMapper}.
 */
@DisplayName("Testes do Mapper de Cartões do Cliente.")
@SpringBootTest
public class CustomerCardMapperTest {

    @Autowired
    private CustomerCardMapper customerCardMapper;

    @Test
    public void givenCostumerCard_whenMappingToDto_thenMapCorrectly() {

        // cenário
        CustomerCard customerCard = CustomerCardUtilTest.createCustomerCardDefault();

        // ação
        CustomerCardResponse customerCardResponse = customerCardMapper.toDto(customerCard);

        // verificação
        assertThat(customerCardResponse.getId()).isEqualTo(customerCard.getId());
        assertThat(customerCardResponse.getCpf()).isEqualTo(customerCard.getCpf());
        assertThat(customerCardResponse.getLimiteCartao()).isEqualTo(customerCard.getCardLimit());
        assertThat(customerCardResponse.getCartoes()).isNotNull();
    }
    
}
