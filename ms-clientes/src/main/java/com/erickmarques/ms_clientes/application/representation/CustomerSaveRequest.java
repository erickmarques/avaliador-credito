package com.erickmarques.ms_clientes.application.representation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerSaveRequest {
    private String cpf;
    private String name;
    private Integer age;
}
