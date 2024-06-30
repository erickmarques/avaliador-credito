package com.erickmarques.ms_cartoes.infra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;

import com.erickmarques.ms_cartoes.domain.Card;
import com.erickmarques.ms_cartoes.domain.CustomerCard;
import com.erickmarques.ms_cartoes.util.CardUtilTest;
import com.erickmarques.ms_cartoes.util.CustomerCardUtilTest;


/**
 * Classe de teste para {@link CustomerCardRepository}.
 */
@ActiveProfiles("test") 
@DataJpaTest
@DisplayName("Testes do Repositório dos Cartões do Cliente.")
public class CustomerCardRepositoryTest {
    
    @Autowired
    private CustomerCardRepository customerCardRepository;

    @Autowired
    private CardRepository cardRepository;

    private List<Card> cards;

    @BeforeEach
    void setUp() {

        //cenário 
        cards = cardRepository.saveAll(CardUtilTest.createCardListDefault());
        customerCardRepository.saveAll(CustomerCardUtilTest.createCustomerCardListDefault(cards));
    }

    @Test
    void givenExistingCustomerCard_whenFindByCpfOrderByCardLimitDesc_thenCustomerCardShouldBeReturned(){
        //ação
        List<CustomerCard> customerCards = customerCardRepository.findByCpfOrderByCardLimitDesc(CustomerCardUtilTest.CPF);

        //verificação
        assertThat(customerCards).hasSize(3);
        assertThat(customerCards.get(0).getId()).isEqualTo(CustomerCardUtilTest.ID);
        assertThat(customerCards.get(0).getCpf()).isEqualTo(CustomerCardUtilTest.CPF);
        assertThat(customerCards.get(0).getCardLimit()).isEqualTo(CustomerCardUtilTest.LIMIT);

    }
}
