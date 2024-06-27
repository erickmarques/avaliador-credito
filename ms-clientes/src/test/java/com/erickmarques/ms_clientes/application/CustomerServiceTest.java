package com.erickmarques.ms_clientes.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.erickmarques.ms_clientes.application.mapper.CustomerMapper;
import com.erickmarques.ms_clientes.application.representation.CustomerSaveRequest;
import com.erickmarques.ms_clientes.application.representation.CustomerSaveResponse;
import com.erickmarques.ms_clientes.domain.Customer;
import com.erickmarques.ms_clientes.infra.CustomerRepository;
import com.erickmarques.ms_clientes.util.CustomerUtilTest;


/**
 * Classe de teste para {@link CustomerService}.
 */
@DisplayName("Testes do Service de Cliente")
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private CustomerSaveRequest customerSaveRequest;
    private CustomerSaveResponse customerSaveResponse;

    @BeforeEach
    void setUp() {
        customer             = CustomerUtilTest.createCostumerDefault();
        customerSaveRequest  = CustomerUtilTest.createCustomerSaveRequestDefault();
        customerSaveResponse = CustomerUtilTest.createCustomerSaveResponseDefault();
    }

    @Nested
    class CreateCustomer {

        @Test
        void testeCreateCustomer(){
            
            // cenário
            when(customerMapper.toEntity(any(CustomerSaveRequest.class))).thenReturn(customer);
            when(customerRepository.save(any(Customer.class))).thenReturn(customer);
            when(customerMapper.toDto(any(Customer.class))).thenReturn(customerSaveResponse);

            // ação
            CustomerSaveResponse customerSaveResponse = customerService.createCustomer(customerSaveRequest);

            // verificação
            CustomerUtilTest.assertCostumerDefault(customer, customerSaveResponse);
        }
    }

    
}
