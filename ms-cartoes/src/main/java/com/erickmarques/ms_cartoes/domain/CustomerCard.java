package com.erickmarques.ms_cartoes.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_cards", indexes = {@Index(name = "idx_cpf", columnList = "cpf")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "id_card")
    private Card card;

    @Column(name = "card_limit")
    private BigDecimal cardLimit;

    @Column
    private String address;
    
}
