package com.erickmarques.ms_avaliador_credito.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.erickmarques.ms_avaliador_credito.domain.response.CardResponse;
import com.erickmarques.ms_avaliador_credito.domain.response.CustomerCardResponse;

import java.util.List;

@FeignClient(value = "ms-cartoes", path = "/api")
public interface CardResourceClient {
    
    @GetMapping(path = "/cliente-cartoes", params = "cpf")
    ResponseEntity<List<CustomerCardResponse>> getCardsByCpf(@RequestParam("cpf") String cpf);

    @GetMapping(path = "/cartoes", params = "renda")
    ResponseEntity<List<CardResponse>> getCardsWithIncomeUpTo(@RequestParam("renda") Long renda);
}
