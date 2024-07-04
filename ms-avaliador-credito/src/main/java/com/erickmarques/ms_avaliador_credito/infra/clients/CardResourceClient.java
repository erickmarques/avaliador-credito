package com.erickmarques.ms_avaliador_credito.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.erickmarques.ms_avaliador_credito.domain.Card;
import com.erickmarques.ms_avaliador_credito.domain.CustomerCard;

import java.util.List;

@FeignClient(value = "ms-cartoes", path = "https://localhost:8092/api")
public interface CardResourceClient {
    
    @GetMapping(params = "/cliente-cartoes/cpf")
    ResponseEntity<List<CustomerCard>> getCardsByCpf(@RequestParam("cpf") String cpf);

    @GetMapping(params = "/cartoes/cpf")
    ResponseEntity<List<Card>> getCardsWithIncomeUpTo(@RequestParam("renda") Long renda);
}
