package com.erickmarques.ms_clientes.application.mapper;


import com.erickmarques.ms_clientes.application.representation.CustomerSaveRequest;
import com.erickmarques.ms_clientes.application.representation.CustomerSaveResponse;
import com.erickmarques.ms_clientes.domain.Customer;

public class CustomerMapper {
    
    public Customer toEntity(CustomerSaveRequest customerSaveRequest){
        Customer customer = new Customer();

        customer.setName(customerSaveRequest.getName());
        customer.setCpf(customerSaveRequest.getCpf());
        customer.setAge(customerSaveRequest.getAge());

        return customer;
    }
    
    public CustomerSaveResponse toDto(Customer customer){
        
        CustomerSaveResponse customerSaveResponse = new CustomerSaveResponse();

        customerSaveResponse.setId(customer.getId());
        customerSaveResponse.setName(customer.getName());
        customerSaveResponse.setCpf(customer.getCpf());
        customerSaveResponse.setAge(customer.getAge());

        return customerSaveResponse;
    }
}
