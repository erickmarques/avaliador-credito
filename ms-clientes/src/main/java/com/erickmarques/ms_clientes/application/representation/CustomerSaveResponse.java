package com.erickmarques.ms_clientes.application.representation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSaveResponse {
    private Long id;
    private String name;
    private String cpf;
    private Integer age;
}
