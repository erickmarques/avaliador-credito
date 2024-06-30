package com.erickmarques.ms_cartoes.infra;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.erickmarques.ms_cartoes.domain.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

    /**
     * Pesquisar um cartão por renda menor ou igual que passada por parametro e orderna pela maior renda.
     * 
     * @param income
     * @return List<Card>
    */
    List<Card> findByIncomeLessThanEqualOrderByIncomeDesc(BigDecimal income);
}
