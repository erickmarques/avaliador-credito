package com.erickmarques.ms_clientes.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.erickmarques.ms_clientes.application.mapper.CustomerMapper;
import com.erickmarques.ms_clientes.application.representation.CustomerSaveRequest;
import com.erickmarques.ms_clientes.domain.Customer;
import com.erickmarques.ms_clientes.infra.CustomerRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional
    public Customer creaCustomer(CustomerSaveRequest customerSaveRequest){
        Customer customer = customerMapper.toEntity(customerSaveRequest);
        return customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    public Optional<Customer> findByCpf(String cpf){
        return customerRepository.findByCpf(cpf);
    }
}
