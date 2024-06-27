package com.erickmarques.ms_clientes.util;

import com.erickmarques.ms_clientes.application.representation.CustomerSaveRequest;
import com.erickmarques.ms_clientes.application.representation.CustomerSaveResponse;
import com.erickmarques.ms_clientes.domain.Customer;

public class CustomerUtilTest {

    public static final Long ID     = 1L;
    public static final String NAME = "Erick Marques";
    public static final String CPF  = "10315487558";
    public static final Integer AGE = 30;

    public static Customer createCostumerDefault(){
        return new Customer(ID, NAME, CPF, AGE);
    }

    public static CustomerSaveResponse createCustomerSaveResponseDefault(){
        return new CustomerSaveResponse(ID, NAME, CPF, AGE);
    }

    public static CustomerSaveRequest createCustomerSaveRequestDefault(){
        return new CustomerSaveRequest(CPF, NAME, AGE);
    }
}
