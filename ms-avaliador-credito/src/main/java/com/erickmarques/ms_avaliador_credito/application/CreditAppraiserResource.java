package com.erickmarques.ms_avaliador_credito.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erickmarques.ms_avaliador_credito.domain.CustomerSituation;

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
}
