package com.erickmarques.ms_clientes.factory;


import com.erickmarques.ms_clientes.application.representation.CustomerSaveRequest;
import com.erickmarques.ms_clientes.application.representation.CustomerSaveResponse;
import com.erickmarques.ms_clientes.domain.Customer;

public class CustomerFactory {

    public static final Long ID     = 1L;
    public static final String NAME = "Erick Marques";
    public static final String CPF  = "87439818016";
    public static final Integer AGE = 30;

    public static Customer createCostumerDefault(){
        return Customer
                .builder()
                .id(ID)
                .name(NAME)
                .cpf(CPF)
                .age(AGE)
                .build();
    }

    public static CustomerSaveResponse createCustomerSaveResponseDefault(){
        return CustomerSaveResponse
                .builder()
                .id(ID)
                .nome(NAME)
                .cpf(CPF)
                .idade(AGE)
                .build();
    }

    public static CustomerSaveRequest createCustomerSaveRequestDefault(){
        return CustomerSaveRequest
                .builder()
                .nome(NAME)
                .cpf(CPF)
                .idade(AGE)
                .build();
    }

}
