package com.erickmarques.ms_clientes.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.erickmarques.ms_clientes.application.representation.CustomerSaveRequest;
import com.erickmarques.ms_clientes.application.representation.CustomerSaveResponse;
import com.erickmarques.ms_clientes.domain.Customer;

public class CustomerUtilTest {

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

    public static void assertCostumerDefault(Customer customer, CustomerSaveResponse customerSaveResponse){
        assertNotNull(customerSaveResponse.getId());
        assertThat(customer.getId()).isEqualTo(customerSaveResponse.getId());
        assertThat(customer.getName()).isEqualTo(customerSaveResponse.getNome());
        assertThat(customer.getCpf()).isEqualTo(customerSaveResponse.getCpf());
        assertThat(customer.getAge()).isEqualTo(customerSaveResponse.getIdade());
    }
}
