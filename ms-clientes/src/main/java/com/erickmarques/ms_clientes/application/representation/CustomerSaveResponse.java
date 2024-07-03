package com.erickmarques.ms_clientes.application.representation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerSaveResponse {
    private final Long id;
    private final String nome;
    private final String cpf;
    private final Integer idade;
}
