package com.erickmarques.ms_avaliador_credito.domain;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CustomerSituation {
    private CustomerData customerData;
    private List<CustomerCard> cards;
}
