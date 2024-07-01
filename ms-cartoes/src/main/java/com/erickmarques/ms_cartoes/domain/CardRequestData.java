package com.erickmarques.ms_cartoes.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardRequestData {
    private Long idCard;
    private String cpf;
    private String address;
    private BigDecimal limitReleased;
}
