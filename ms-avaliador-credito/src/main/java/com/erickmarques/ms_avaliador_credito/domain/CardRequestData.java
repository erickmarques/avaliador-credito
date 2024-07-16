package com.erickmarques.ms_avaliador_credito.domain;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CardRequestData {

    @NotNull(message = "É preciso informar o ID do cartão!")
    private final Long idCard;

    @NotBlank(message = "É preciso informar o CPF!")
    @CPF(message = "É preciso informar um CPF válido!")
    private final String cpf;

    @NotBlank(message = "É preciso informar o endereço!")
    private final String address;

    @NotNull(message = "É preciso informar o limite liberado!")
    private final BigDecimal limitReleased;
}
