package com.erickmarques.ms_cartoes.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erickmarques.ms_cartoes.domain.CustomerCard;

public interface CustomerCardRepository extends JpaRepository<CustomerCard, Long> {
    
}
