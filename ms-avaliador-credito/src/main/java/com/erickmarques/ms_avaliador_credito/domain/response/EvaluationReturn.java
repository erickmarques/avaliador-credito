package com.erickmarques.ms_avaliador_credito.domain.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EvaluationReturn {
    private final List<ApprovedCard> cartoes;
}
