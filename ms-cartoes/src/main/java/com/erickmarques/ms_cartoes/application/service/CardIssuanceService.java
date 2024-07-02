package com.erickmarques.ms_cartoes.application.service;

import org.springframework.stereotype.Service;

import com.erickmarques.ms_cartoes.domain.CardRequestData;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardIssuanceService {

    private final CustomerCardService customerCardService;
    private final ObjectMapper mapper;

    public void processIssuanceRequest(String payload) {
        try {
            CardRequestData data = mapper.readValue(payload, CardRequestData.class);
            
            customerCardService.save(data);
        } catch (Exception e) {
            log.error("Error processing card issuance request", e);
        }
    }
}
