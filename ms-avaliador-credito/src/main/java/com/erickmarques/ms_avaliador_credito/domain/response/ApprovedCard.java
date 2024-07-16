package com.erickmarques.ms_avaliador_credito.domain.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApprovedCard {
    private final String card;
    private final String flag;
    private final BigDecimal approvedLimit;
    
}
