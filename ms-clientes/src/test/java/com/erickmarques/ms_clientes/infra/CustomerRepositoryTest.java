package com.erickmarques.ms_clientes.infra;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.erickmarques.ms_clientes.domain.Customer;
import com.erickmarques.ms_clientes.util.CustomerUtilTest;


/**
 * Classe de teste para {@link CustomerRepository}.
 */
@ActiveProfiles("test") 
@DataJpaTest
@DisplayName("Testes do Repositório do Cliente.")
public class CustomerRepositoryTest {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    private Customer customerDefault;

    @BeforeEach
    void setUp() {

        //cenário 
        customerDefault = CustomerUtilTest.createCostumerDefault();
        customerRepository.save(customerDefault);
    }

    @Test
    void givenExistingCustomer_whenFindByCpf_thenCustomerShouldBeReturned(){
        //ação
        Optional<Customer> customer = customerRepository.findByCpf(CustomerUtilTest.CPF);

        //verificação
        assertThat(customer).isPresent();
        assertThat(customer.get().getId()).isEqualTo(customerDefault.getId());
        assertThat(customer.get().getName()).isEqualTo(customerDefault.getName());
        assertThat(customer.get().getCpf()).isEqualTo(customerDefault.getCpf());
        assertThat(customer.get().getAge()).isEqualTo(customerDefault.getAge());

    }

    @Test
    void givenNonExistingCustomer_whenFindByCpf_thenNoCustomerShouldBeReturned(){
        //ação
        Optional<Customer> customer = customerRepository.findByCpf("NON_EXISTING_CPF");

        //verificação
        assertThat(customer).isNotPresent();
    }
}
