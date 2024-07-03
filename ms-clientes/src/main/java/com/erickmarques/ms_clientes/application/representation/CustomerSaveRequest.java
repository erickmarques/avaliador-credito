package com.erickmarques.ms_clientes.application.representation;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerSaveRequest {

    @NotBlank(message = "Favor informar o nome!")
    private final String nome;
    
    @NotBlank(message = "Favor informar o CPF!")
    @CPF(message = "Favor informar um CPF válido!")
    private final String cpf;

    @NotNull(message = "Favor informar a idade!")
    private final Integer idade;
}
