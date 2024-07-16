package com.erickmarques.ms_avaliador_credito.domain.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CardResponse {
    private final Long id;
    private final String nome;
    private final String bandeiraCartao;
    private final BigDecimal renda;
    private final BigDecimal limiteBasico;
}
