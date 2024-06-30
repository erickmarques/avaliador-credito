package com.erickmarques.ms_cartoes.application.representation;

import java.math.BigDecimal;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardSaveRequest {
    @NotNull(message = "Favor informar o nome do cartão!")
    @NotBlank(message = "Favor informar o nome do cartão!")
    private String nome;

    @NotNull(message = "Favor informar a bandeira do cartão!")
    private String bandeiraCartao;

    @NotNull(message = "Favor informar a renda do cartão!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Renda tem que valor maior que zero.")
    private BigDecimal renda;

    @NotNull(message = "Favor informar o limite básico do cartão!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Limite básico tem que valor maior que zero.")
    private BigDecimal limiteBasico;
}
