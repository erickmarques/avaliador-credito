package com.erickmarques.ms_clientes.application;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erickmarques.ms_clientes.application.representation.CustomerSaveRequest;
import com.erickmarques.ms_clientes.application.representation.CustomerSaveResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class CustomerResource {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerSaveResponse> createCustomer(@Valid @RequestBody CustomerSaveRequest customerSaveRequest){

        CustomerSaveResponse customerSaveResponse = customerService.createCustomer(customerSaveRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerSaveResponse);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<CustomerSaveResponse> findByCpf(@RequestParam("cpf") String cpf){
        CustomerSaveResponse customerSaveResponse = customerService.findByCpf(cpf);
        return ResponseEntity.ok(customerSaveResponse);
    }
}