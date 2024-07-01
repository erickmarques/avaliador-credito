package com.erickmarques.ms_cartoes.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cards", indexes = {@Index(name = "idx_income", columnList = "income")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    
    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_flag")
    private CardFlag cardFlag;
    
    @Column
    private BigDecimal income;

    @Column(name = "basic_limit")
    private BigDecimal basicLimit;

    
}
