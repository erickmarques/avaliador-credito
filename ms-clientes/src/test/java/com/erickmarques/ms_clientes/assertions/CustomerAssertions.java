package com.erickmarques.ms_clientes.assertions;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.erickmarques.ms_clientes.application.representation.CustomerSaveResponse;
import com.erickmarques.ms_clientes.domain.Customer;
import static org.assertj.core.api.Assertions.assertThat;

public final class CustomerAssertions {

    public static void assertCustomerDefault(Customer customer, CustomerSaveResponse customerSaveResponse) {
        assertNotNull(customerSaveResponse.getId());
        assertThat(customer.getId()).isEqualTo(customerSaveResponse.getId());
        assertThat(customer.getName()).isEqualTo(customerSaveResponse.getNome());
        assertThat(customer.getCpf()).isEqualTo(customerSaveResponse.getCpf());
        assertThat(customer.getAge()).isEqualTo(customerSaveResponse.getIdade());
    }
    
}
