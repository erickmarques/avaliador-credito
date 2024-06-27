package com.erickmarques.ms_clientes.application.representation;

import lombok.Getter;

@Getter
public class CustomerSaveRequest {
    private String cpf;
    private String nome;
    private Integer idade;
}
