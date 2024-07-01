package com.erickmarques.ms_cartoes.application.representation;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerCardResponse {
    private Long id;
    private String cpf;
    private BigDecimal limiteCartao;
    private CardSaveResponse cartoes;
}
