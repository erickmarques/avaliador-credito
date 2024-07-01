package com.erickmarques.ms_cartoes.application.representation;

import java.math.BigDecimal;

import com.erickmarques.ms_cartoes.domain.CardFlag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardSaveResponse {
    private Long id;
    private String nome;
    private CardFlag bandeiraCartao;
    private BigDecimal renda;
    private BigDecimal limiteBasico;
}
