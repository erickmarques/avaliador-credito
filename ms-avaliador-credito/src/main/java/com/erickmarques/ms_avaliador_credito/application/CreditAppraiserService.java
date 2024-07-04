package com.erickmarques.ms_avaliador_credito.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.erickmarques.ms_avaliador_credito.domain.response.CustomerCardResponse;
import com.erickmarques.ms_avaliador_credito.domain.response.CustomerResponse;
import com.erickmarques.ms_avaliador_credito.domain.response.CustomerSituation;
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
            ResponseEntity<CustomerResponse> customerDataResponse = customerResourceClient.getCustomerData(cpf);
            ResponseEntity<List<CustomerCardResponse>> cardsResponse  = cardResourceClient.getCardsByCpf(cpf);

            return CustomerSituation
                        .builder()
                        .cliente(customerDataResponse.getBody())
                        .cartoes(cardsResponse.getBody())
                        .build();

        }catch (FeignException.FeignClientException e){
            throw new ResponseStatusException(HttpStatus.valueOf(e.status()), e.getMessage());
        }
    }
}
