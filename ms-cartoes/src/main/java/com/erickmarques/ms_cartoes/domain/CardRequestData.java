package com.erickmarques.ms_cartoes.domain;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CardRequestData {
    private Long idCard;
    private String cpf;
    private String address;
    private BigDecimal limitReleased;
}
