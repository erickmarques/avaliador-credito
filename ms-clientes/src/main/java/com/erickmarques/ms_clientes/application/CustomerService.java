package com.erickmarques.ms_clientes.application;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.erickmarques.ms_clientes.application.mapper.CustomerMapper;
import com.erickmarques.ms_clientes.application.representation.CustomerSaveRequest;
import com.erickmarques.ms_clientes.application.representation.CustomerSaveResponse;
import com.erickmarques.ms_clientes.domain.Customer;
import com.erickmarques.ms_clientes.infra.CustomerRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional
    public CustomerSaveResponse createCustomer(CustomerSaveRequest customerSaveRequest){

        if (customerRepository.findByCpf(customerSaveRequest.getCpf()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado!");
        }

        Customer customer = customerMapper.toEntity(customerSaveRequest);
        Customer saveCosumer = customerRepository.save(customer);
        return customerMapper.toDto(saveCosumer);
    }

    @Transactional(readOnly = true)
    public CustomerSaveResponse findByCpf(String cpf){

        Customer customer = customerRepository.findByCpf(cpf)
                                .orElseThrow(() -> new ResponseStatusException(
                                    HttpStatus.NOT_FOUND, 
                                    "Cliente não encontrado para o cpf: " + cpf));;

        return customerMapper.toDto(customer);
    }
}
