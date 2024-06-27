package com.erickmarques.ms_clientes.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.erickmarques.ms_clientes.application.representation.CustomerSaveRequest;
import com.erickmarques.ms_clientes.application.representation.CustomerSaveResponse;
import com.erickmarques.ms_clientes.domain.Customer;

@Mapper
public interface CustomerMapper {
    
    @Mapping(target = "id", ignore = true) 
    Customer ToEntity(CustomerSaveRequest customerSaveRequest);
    
    CustomerSaveResponse toDto(Customer customer);
}
