package com.erickmarques.ms_avaliador_credito.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CardRequestProtocol {
    private final String protocol;
}
