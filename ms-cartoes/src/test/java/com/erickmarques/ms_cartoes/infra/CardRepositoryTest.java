package com.erickmarques.ms_cartoes.infra;

import java.math.BigDecimal;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.erickmarques.ms_cartoes.domain.Card;
import com.erickmarques.ms_cartoes.factory.CardFactory;
import com.erickmarques.ms_cartoes.infra.repository.CardRepository;

/**
 * Classe de teste para {@link CardRepository}.
 */
@ActiveProfiles("test") 
@DataJpaTest
@DisplayName("Testes do Repositório do Cartão.")
public class CardRepositoryTest {
    
    @Autowired
    private CardRepository cardRepository;

    @BeforeEach
    void setUp() {

        //cenário 
        cardRepository.saveAll(CardFactory.createCardListDefault());
    }

    @Test
    void givenExistingCard_whenFindByIncomeLessThanEqual_thenCardShouldBeReturned(){
        //ação
        List<Card> cards = cardRepository.findByIncomeLessThanEqualOrderByIncomeDesc(BigDecimal.valueOf(6000));

        //verificação
        assertThat(cards).hasSize(2);
        assertThat(cards.get(0).getId()).isEqualTo(CardFactory.ID);
        assertThat(cards.get(0).getName()).isEqualTo(CardFactory.NAME);
        assertThat(cards.get(0).getCardFlag()).isEqualTo(CardFactory.MASTERCARD);
        assertThat(cards.get(0).getIncome()).isEqualTo(CardFactory.INCOME);
        assertThat(cards.get(0).getBasicLimit()).isEqualTo(CardFactory.BASIC_LIMIT);

    }
}
