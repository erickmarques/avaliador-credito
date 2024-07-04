package com.erickmarques.ms_avaliador_credito.domain;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardRequestData {
    private final Long idCard;
    private final String cpf;
    private final String address;
    private final BigDecimal limitReleased;
}
