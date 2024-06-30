package com.erickmarques.ms_clientes.application.representation;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSaveRequest {

    @NotBlank(message = "Favor informar o nome!")
    private String nome;
    
    @NotBlank(message = "Favor informar o CPF!")
    @CPF(message = "Favor informar um CPF válido!")
    private String cpf;

    @NotNull(message = "Favor informar a idade!")
    private Integer idade;
}
