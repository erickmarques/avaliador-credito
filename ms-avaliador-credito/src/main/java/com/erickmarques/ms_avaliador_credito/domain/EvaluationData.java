package com.erickmarques.ms_avaliador_credito.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EvaluationData {
    private final String cpf;
    private final Long income;
}
