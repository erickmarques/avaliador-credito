package com.erickmarques.ms_avaliador_credito.domain;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Card {
    private final Long id;
    private final String name;
    private final String flag;
    private final BigDecimal basicLimit;
}
