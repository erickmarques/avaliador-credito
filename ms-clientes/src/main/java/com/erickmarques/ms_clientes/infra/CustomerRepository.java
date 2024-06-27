package com.erickmarques.ms_clientes.infra;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erickmarques.ms_clientes.domain.Customer;

public interface CustomerRepository  extends JpaRepository<Customer, Long> {

    /**
     * Pesquisar um cliente a partir do seu cpf.
     * 
     * @param cpf
     * @return Optional<Customer>
    */
    Optional<Customer> findByCpf(String cpf);
}
