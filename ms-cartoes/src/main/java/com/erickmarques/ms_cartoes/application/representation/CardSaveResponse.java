package com.erickmarques.ms_cartoes.application.representation;

import java.math.BigDecimal;

import com.erickmarques.ms_cartoes.domain.CardFlag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardSaveResponse {
    private Long id;
    private String name;
    private CardFlag cardFlag;
    private BigDecimal income;
    private BigDecimal basicLimit;
}
