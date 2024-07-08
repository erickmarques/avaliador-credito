package com.erickmarques.ms_avaliador_credito.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erickmarques.ms_avaliador_credito.domain.CardRequestData;
import com.erickmarques.ms_avaliador_credito.domain.CardRequestProtocol;
import com.erickmarques.ms_avaliador_credito.domain.EvaluationData;
import com.erickmarques.ms_avaliador_credito.domain.response.CustomerSituation;
import com.erickmarques.ms_avaliador_credito.domain.response.EvaluationReturn;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/avaliacoes-credito")
@RequiredArgsConstructor
public class CreditAppraiserResource {
    
    private final CreditAppraiserService creditAppraiserService;

    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity<CustomerSituation> consultarSituacaoCliente(@RequestParam("cpf") String cpf){
        CustomerSituation customerSituation = creditAppraiserService.getSituation(cpf);
        return ResponseEntity.ok(customerSituation);
    }

    @PostMapping
    public ResponseEntity<EvaluationReturn> realizarAvaliacao(@Valid @RequestBody EvaluationData evaluationData){
        EvaluationReturn evaluationReturn = creditAppraiserService.realizeEvaluation(evaluationData);
        return ResponseEntity.ok(evaluationReturn);
    }

    @PostMapping("solicitacoes-cartao")
    public ResponseEntity<CardRequestProtocol> solicitarCartao(@Valid @RequestBody CardRequestData cardRequestData){
        CardRequestProtocol cardRequestProtocol = creditAppraiserService.requestCardIssuance(cardRequestData);
        return ResponseEntity.ok(cardRequestProtocol);
    }
}
