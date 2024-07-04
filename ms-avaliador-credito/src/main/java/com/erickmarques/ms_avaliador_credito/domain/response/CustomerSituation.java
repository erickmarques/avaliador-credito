package com.erickmarques.ms_avaliador_credito.domain.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CustomerSituation {
    private CustomerResponse cliente;
    private List<CustomerCardResponse> cartoes;
}
