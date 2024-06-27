package com.erickmarques.ms_clientes.application.mapper;

import org.mapstruct.Mapper;

import com.erickmarques.ms_clientes.application.representation.CustomerSaveRequest;
import com.erickmarques.ms_clientes.application.representation.CustomerSaveResponse;
import com.erickmarques.ms_clientes.domain.Customer;

@Mapper
public interface CustomerMapper {
    Customer ToEntity(CustomerSaveRequest customerSaveRequest);
    CustomerSaveResponse toDto(Customer customer);
}
