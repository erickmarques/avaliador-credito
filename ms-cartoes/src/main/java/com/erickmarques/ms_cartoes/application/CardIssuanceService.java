package com.erickmarques.ms_cartoes.application;

import org.springframework.stereotype.Service;

import com.erickmarques.ms_cartoes.application.mapper.CustomerCardMapper;
import com.erickmarques.ms_cartoes.domain.Card;
import com.erickmarques.ms_cartoes.domain.CardRequestData;
import com.erickmarques.ms_cartoes.domain.CustomerCard;
import com.erickmarques.ms_cartoes.infra.repository.CardRepository;
import com.erickmarques.ms_cartoes.infra.repository.CustomerCardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardIssuanceService {

    private final CardRepository cardRepository;
    private final CustomerCardRepository customerCardRepository;
    private final ObjectMapper mapper;
    private final CustomerCardMapper customerCardMapper;

    public void processIssuanceRequest(String payload) {
        try {
            CardRequestData data      = mapper.readValue(payload, CardRequestData.class);
            Card card                 = cardRepository.findById(data.getIdCard()).orElseThrow();
            CustomerCard customerCard = customerCardMapper.toEntity(data, card);

            customerCardRepository.save(customerCard);
        } catch (Exception e) {
            log.error("Error processing card issuance request", e);
        }
    }
}
