package com.erickmarques.ms_cartoes.application;

import org.springframework.stereotype.Service;
import java.util.List;

import com.erickmarques.ms_cartoes.application.mapper.CustomerCardMapper;
import com.erickmarques.ms_cartoes.application.representation.CustomerCardResponse;
import com.erickmarques.ms_cartoes.infra.CustomerCardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerCardService {
    
    private final CustomerCardRepository customerCardRepository;
    private final CustomerCardMapper customerCardMapper;

    public List<CustomerCardResponse> findCardsByCpf(String cpf){
        return customerCardRepository
                .findByCpfOrderByCardLimitDesc(cpf)
                .stream()
                .map(customerCard -> customerCardMapper.toDto(customerCard))
                .toList();
    }
}
