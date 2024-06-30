package com.erickmarques.ms_cartoes.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.erickmarques.ms_cartoes.domain.CustomerCard;

@Repository
public interface CustomerCardRepository extends JpaRepository<CustomerCard, Long> {
    
    /**
     * Pesquisar cartões de um cliente por cpf ordernando pelo maior limite do cartão.
     * 
     * @param cpf
     * @return List<CustomerCard>
    */
    List<CustomerCard> findByCpfOrderByCardLimitDesc(String cpf);
}
