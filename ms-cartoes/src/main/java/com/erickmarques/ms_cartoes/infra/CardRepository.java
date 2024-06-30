package com.erickmarques.ms_cartoes.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erickmarques.ms_cartoes.domain.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
    
}
