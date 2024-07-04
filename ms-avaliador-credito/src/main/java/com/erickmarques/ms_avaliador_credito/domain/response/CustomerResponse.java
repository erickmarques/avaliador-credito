package com.erickmarques.ms_avaliador_credito.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {
    private final Long id;
    private final String nome;
    private final String cpf;
    private final Integer idade;
}
