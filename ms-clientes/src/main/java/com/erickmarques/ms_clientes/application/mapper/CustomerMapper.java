package com.erickmarques.ms_clientes.application.mapper;


import org.springframework.stereotype.Component;

import com.erickmarques.ms_clientes.application.representation.CustomerSaveRequest;
import com.erickmarques.ms_clientes.application.representation.CustomerSaveResponse;
import com.erickmarques.ms_clientes.domain.Customer;

@Component
public class CustomerMapper {
    
    public Customer toEntity(CustomerSaveRequest customerSaveRequest){
        return
            Customer.builder()
                    .name(customerSaveRequest.getNome())
                    .cpf(customerSaveRequest.getCpf())
                    .age(customerSaveRequest.getIdade())
                    .build();
    }
    
    public CustomerSaveResponse toDto(Customer customer){
        return 
        CustomerSaveResponse.builder()
                            .id(customer.getId())
                            .nome(customer.getName())
                            .cpf(customer.getCpf())
                            .idade(customer.getAge())
                            .build();
    }
}
