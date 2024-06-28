package com.erickmarques.ms_clientes.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
        @DisplayName("Teste para criar um cliente com dados válidos.")
        void givenNewCustomer_whenCreateCustomer_thenCustomerIsCreatedSuccessfully(){
            
            // cenário
            when(customerMapper.toEntity(any(CustomerSaveRequest.class))).thenReturn(customer);
            when(customerRepository.save(any(Customer.class))).thenReturn(customer);
            when(customerMapper.toDto(any(Customer.class))).thenReturn(customerSaveResponse);

            // ação
            CustomerSaveResponse customerSaveResponse = customerService.createCustomer(customerSaveRequest);

            // verificação
            verify(customerRepository, times(1)).save(any(Customer.class));
            CustomerUtilTest.assertCostumerDefault(customer, customerSaveResponse);
        }

        @Test
        @DisplayName("Teste para criar um cliente com cpf já existente.")
        void givenCustomerWithExistingCpf_whenCreateCustomer_thenResponseStatusException(){
            
            // cenário
            when(customerRepository.findByCpf(anyString())).thenReturn(Optional.of(new Customer()));

            // ação
            ResponseStatusException exception = assertThrows(ResponseStatusException.class, 
                () -> customerService.createCustomer(customerSaveRequest));

            // verificação
            assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
            assertThat(exception.getReason()).isEqualTo("CPF já cadastrado!");
            verify(customerRepository).findByCpf(anyString());
            verify(customerRepository, never()).save(any(Customer.class));
        }
    }

    @Nested
    class FindByCpf {

        @Test
        @DisplayName("Teste para pesquisar um cliente por cpf.")
        void givenCustomerWithExistingCpf_whenFindByCpf_thenReturnCustomer(){
            
            // cenário
            when(customerRepository.findByCpf(anyString())).thenReturn(Optional.of(new Customer()));
            when(customerMapper.toDto(any(Customer.class))).thenReturn(customerSaveResponse);

            // ação
           CustomerSaveResponse customerSaveResponse = customerService.findByCpf(anyString());

            // verificação
            verify(customerRepository, times(1)).findByCpf(anyString());
            CustomerUtilTest.assertCostumerDefault(customer, customerSaveResponse);
        }

        @Test
        @DisplayName("Teste para pesquisar um cliente por cpf inexistente.")
        void givenCustomerWithNotExistingCpf_whenFindByCpf_thenResponseStatusException(){
            
            // cenário
            when(customerRepository.findByCpf(CustomerUtilTest.CPF)).thenReturn(Optional.empty());
            
            // ação
            ResponseStatusException exception = assertThrows(ResponseStatusException.class, 
            () -> customerService.findByCpf(CustomerUtilTest.CPF));
        
            // verificação
            assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(exception.getReason()).isEqualTo("Cliente não encontrado para o cpf: " + CustomerUtilTest.CPF);
        }
    }
}
