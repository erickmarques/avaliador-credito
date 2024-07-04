package com.erickmarques.ms_avaliador_credito.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.erickmarques.ms_avaliador_credito.domain.CustomerCard;
import com.erickmarques.ms_avaliador_credito.domain.CustomerData;
import com.erickmarques.ms_avaliador_credito.domain.CustomerSituation;
import com.erickmarques.ms_avaliador_credito.infra.clients.CardResourceClient;
import com.erickmarques.ms_avaliador_credito.infra.clients.CustomerResourceClient;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditAppraiserService {
    
    private final CardResourceClient cardResourceClient;
    private final CustomerResourceClient customerResourceClient;

    public CustomerSituation getSituation(String cpf){
        try {
            ResponseEntity<CustomerData> customerDataResponse = customerResourceClient.getCustomerData(cpf);
            ResponseEntity<List<CustomerCard>> cardsResponse  = cardResourceClient.getCardsByCpf(cpf);

            return CustomerSituation
                        .builder()
                        .customerData(customerDataResponse.getBody())
                        .cards(cardsResponse.getBody())
                        .build();

        }catch (FeignException.FeignClientException e){
            throw new ResponseStatusException(HttpStatus.valueOf(e.status()), e.getMessage());
        }
    }
}
