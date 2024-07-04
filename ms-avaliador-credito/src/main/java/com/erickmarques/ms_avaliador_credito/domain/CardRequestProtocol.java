package com.erickmarques.ms_avaliador_credito.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardRequestProtocol {
    private final String protocol;
}
