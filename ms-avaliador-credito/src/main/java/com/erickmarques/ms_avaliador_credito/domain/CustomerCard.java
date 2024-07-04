package com.erickmarques.ms_avaliador_credito.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerCard {
    private final String name;
    private final String flag;
    private final String limitReleased;
}
