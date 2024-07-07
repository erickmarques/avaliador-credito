package com.erickmarques.ms_avaliador_credito.domain.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApprovedCard {
    private final String card;
    private final String flag;
    private final BigDecimal approvedLimit;
    
}
