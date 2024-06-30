package com.erickmarques.ms_clientes.application.mapper;


import org.springframework.stereotype.Component;

import com.erickmarques.ms_clientes.application.representation.CustomerSaveRequest;
import com.erickmarques.ms_clientes.application.representation.CustomerSaveResponse;
import com.erickmarques.ms_clientes.domain.Customer;

@Component
public class CustomerMapper {
    
    public Customer toEntity(CustomerSaveRequest customerSaveRequest){
        Customer customer = new Customer();

        customer.setName(customerSaveRequest.getNome());
        customer.setCpf(customerSaveRequest.getCpf());
        customer.setAge(customerSaveRequest.getIdade());

        return customer;
    }
    
    public CustomerSaveResponse toDto(Customer customer){
        
        CustomerSaveResponse customerSaveResponse = new CustomerSaveResponse();

        customerSaveResponse.setId(customer.getId());
        customerSaveResponse.setNome(customer.getName());
        customerSaveResponse.setCpf(customer.getCpf());
        customerSaveResponse.setIdade(customer.getAge());

        return customerSaveResponse;
    }
}
