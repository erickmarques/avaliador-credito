package com.erickmarques.ms_avaliador_credito.application;

import org.springframework.stereotype.Service;

import com.erickmarques.ms_avaliador_credito.domain.CustomerSituation;
import com.erickmarques.ms_avaliador_credito.infra.clients.CardResourceClient;
import com.erickmarques.ms_avaliador_credito.infra.clients.CustomerResourceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreditAppraiserService {
    
    private final CardResourceClient cardResourceClient;
    private final CustomerResourceClient customerResourceClient;

    public CustomerSituation getSituation(String cpf){
        return null;
    }


}
