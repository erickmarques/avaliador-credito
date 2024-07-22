package com.erickmarques.ms_avaliador_credito.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.erickmarques.ms_avaliador_credito.domain.response.CustomerResponse;

@FeignClient(value = "ms-clientes", path = "/api/clientes")
public interface CustomerResourceClient {
    
    @GetMapping(params = "cpf")
    ResponseEntity<CustomerResponse> getCustomerData(@RequestParam("cpf") String cpf);
}
