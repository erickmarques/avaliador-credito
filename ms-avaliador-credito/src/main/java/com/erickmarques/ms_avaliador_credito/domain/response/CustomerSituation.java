package com.erickmarques.ms_avaliador_credito.domain.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CustomerSituation {
    private CustomerResponse cliente;
    private List<CustomerCardResponse> cartoes;
}
