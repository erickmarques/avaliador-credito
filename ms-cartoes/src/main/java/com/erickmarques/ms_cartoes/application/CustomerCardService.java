package com.erickmarques.ms_cartoes.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.erickmarques.ms_cartoes.application.mapper.CustomerCardMapper;
import com.erickmarques.ms_cartoes.application.representation.CustomerCardResponse;
import com.erickmarques.ms_cartoes.domain.Card;
import com.erickmarques.ms_cartoes.domain.CardRequestData;
import com.erickmarques.ms_cartoes.domain.CustomerCard;
import com.erickmarques.ms_cartoes.infra.repository.CustomerCardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerCardService {
    
    private final CustomerCardRepository customerCardRepository;
    private final CustomerCardMapper customerCardMapper;
    private final CardService cardService;

    @Transactional(readOnly = true)
    public List<CustomerCardResponse> findCardsByCpf(String cpf){
        return customerCardRepository
                .findByCpfOrderByCardLimitDesc(cpf)
                .stream()
                .map(customerCard -> customerCardMapper.toDto(customerCard))
                .toList();
    }

    @Transactional
    public void save(CardRequestData data){
        Card card = cardService.findById(data.getIdCard());
        CustomerCard customerCard = customerCardMapper.toEntity(data, card);

        customerCardRepository.save(customerCard);
    }
}
