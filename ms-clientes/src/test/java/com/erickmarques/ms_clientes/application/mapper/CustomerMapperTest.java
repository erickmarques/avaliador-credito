package com.erickmarques.ms_clientes.application.mapper;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.erickmarques.ms_clientes.application.representation.CustomerSaveRequest;
import com.erickmarques.ms_clientes.application.representation.CustomerSaveResponse;
import com.erickmarques.ms_clientes.domain.Customer;
import com.erickmarques.ms_clientes.util.CustomerUtilTest;

public class CustomerMapperTest {

    private CustomerMapper customerMapper = new CustomerMapper();

    @Test
    public void givenCustomerSaveRequest_whenMappingToEntity_thenMapCorrectly() {

        // cenário
        CustomerSaveRequest customerSaveRequest  = CustomerUtilTest.createCustomerSaveRequestDefault();

        // ação
        Customer customer = customerMapper.toEntity(customerSaveRequest);

        // verificação
        assertThat(customerSaveRequest.getCpf()).isEqualTo(customer.getCpf());
        assertThat(customerSaveRequest.getName()).isEqualTo(customer.getName());
        assertThat(customerSaveRequest.getAge()).isEqualTo(customer.getAge());
    }

    @Test
    public void givenCustomer_whenMappingToDto_thenMapCorrectly() {

        // cenário
        Customer customer = CustomerUtilTest.createCostumerDefault();

         // ação
         CustomerSaveResponse customerSaveResponse = customerMapper.toDto(customer);

         // verificação
        assertThat(customer.getId()).isEqualTo(customerSaveResponse.getId());
        assertThat(customer.getName()).isEqualTo(customerSaveResponse.getName());
        assertThat(customer.getCpf()).isEqualTo(customerSaveResponse.getCpf());
        assertThat(customer.getAge()).isEqualTo(customerSaveResponse.getAge());
    }
}
