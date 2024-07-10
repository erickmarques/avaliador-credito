package com.erickmarques.ms_avaliador_credito.domain;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EvaluationData {

    @NotBlank(message = "É preciso informar o CPF!")
    @CPF(message = "É preciso informar um CPF válido!")
    private final String cpf;

    @NotNull(message = "É preicos informar a renda!")
    private final BigDecimal income;
}
