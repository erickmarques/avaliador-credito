package com.erickmarques.ms_avaliador_credito.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerData {
    private final Long id;
    private final String name;
    private final Integer age;
}
