package com.erickmarques.ms_cartoes.application.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.erickmarques.ms_cartoes.application.representation.CustomerCardResponse;
import com.erickmarques.ms_cartoes.domain.Card;
import com.erickmarques.ms_cartoes.domain.CardRequestData;
import com.erickmarques.ms_cartoes.domain.CustomerCard;
import com.erickmarques.ms_cartoes.factory.CardFactory;
import com.erickmarques.ms_cartoes.factory.CustomerCardFactory;

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
        CustomerCard customerCard = CustomerCardFactory.createCustomerCardDefault();

        // ação
        CustomerCardResponse customerCardResponse = customerCardMapper.toDto(customerCard);

        // verificação
        assertThat(customerCardResponse.getId()).isEqualTo(customerCard.getId());
        assertThat(customerCardResponse.getCpf()).isEqualTo(customerCard.getCpf());
        assertThat(customerCardResponse.getLimiteCartao()).isEqualTo(customerCard.getCardLimit());
        assertThat(customerCardResponse.getCartoes()).isNotNull();
    }

    @Test
    public void givenCostumerCard_whenMappingToEntity_thenMapCorrectly() {

        // cenário
        CardRequestData cardRequestData = CustomerCardFactory.createCardRequestDataDefault();
        Card card = CardFactory.createCardDefault();

        // ação
        CustomerCard customerCard = customerCardMapper.toEntity(cardRequestData, card);

        // verificação
        assertThat(customerCard.getCpf()).isEqualTo(cardRequestData.getCpf());
        assertThat(customerCard.getAddress()).isEqualTo(cardRequestData.getAddress());
        assertThat(customerCard.getCardLimit()).isEqualTo(cardRequestData.getLimitReleased());
        assertThat(customerCard.getCard()).isNotNull();
    }
    
}
