package com.erickmarques.ms_cartoes.application.resource;

import org.springframework.http.ResponseEntity;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erickmarques.ms_cartoes.application.representation.CustomerCardResponse;
import com.erickmarques.ms_cartoes.application.service.CustomerCardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cliente-cartoes")
@RequiredArgsConstructor
public class CustomerCardResource {

    private final CustomerCardService customerCardService;

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CustomerCardResponse>> pesquisarCartoesPorCpf(@RequestParam("cpf") String cpf){
        List<CustomerCardResponse> customerCards = customerCardService.findCardsByCpf(cpf);
        return ResponseEntity.ok(customerCards);
    }
}
