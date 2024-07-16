package com.erickmarques.ms_avaliador_credito.domain.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomerCardResponse {
    private final Long id;
    private final String cpf;
    private final BigDecimal limiteCartao;
    private final CardResponse cartoes;
}
