package com.erickmarques.ms_cartoes.application.representation;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerCardResponse {
    private final Long id;
    private final String cpf;
    private final BigDecimal limiteCartao;
    private final CardSaveResponse cartoes;
}
