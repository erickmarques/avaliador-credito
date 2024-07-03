package com.erickmarques.ms_cartoes.application.representation;

import java.math.BigDecimal;

import com.erickmarques.ms_cartoes.domain.CardFlag;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardSaveResponse {
    private final Long id;
    private final String nome;
    private final CardFlag bandeiraCartao;
    private final BigDecimal renda;
    private final BigDecimal limiteBasico;
}
