package com.erickmarques.ms_avaliador_credito.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import com.erickmarques.ms_avaliador_credito.domain.CardRequestData;
import com.erickmarques.ms_avaliador_credito.domain.CardRequestProtocol;
import com.erickmarques.ms_avaliador_credito.domain.EvaluationData;
import com.erickmarques.ms_avaliador_credito.domain.response.ApprovedCard;
import com.erickmarques.ms_avaliador_credito.domain.response.CardResponse;
import com.erickmarques.ms_avaliador_credito.domain.response.CustomerCardResponse;
import com.erickmarques.ms_avaliador_credito.domain.response.CustomerResponse;
import com.erickmarques.ms_avaliador_credito.domain.response.CustomerSituation;
import com.erickmarques.ms_avaliador_credito.domain.response.EvaluationReturn;
import com.erickmarques.ms_avaliador_credito.infra.clients.CardIssuanceRequestPublisher;
import com.erickmarques.ms_avaliador_credito.infra.clients.CardResourceClient;
import com.erickmarques.ms_avaliador_credito.infra.clients.CustomerResourceClient;
import com.fasterxml.jackson.core.JsonProcessingException;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CreditAppraiserService {
    
    private final CardResourceClient cardResourceClient;
    private final CustomerResourceClient customerResourceClient;
    private final CardIssuanceRequestPublisher cardIssuanceRequestPublisher;

    public CustomerSituation getSituation(String cpf){
        try {
            ResponseEntity<CustomerResponse> customerDataResponse = customerResourceClient.getCustomerData(cpf);
            ResponseEntity<List<CustomerCardResponse>> cardsResponse  = cardResourceClient.getCardsByCpf(cpf);

            if (customerDataResponse == null || cardsResponse == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço de cliente/cartão indisponível!");
            }

            return CustomerSituation
                        .builder()
                        .cliente(customerDataResponse.getBody())
                        .cartoes(cardsResponse.getBody())
                        .build();

        }catch (FeignException.FeignClientException e){
            throw new ResponseStatusException(HttpStatus.valueOf(e.status()), e.getMessage());
        }
    }

    public EvaluationReturn realizeEvaluation(EvaluationData evaluationData){

        try {
            ResponseEntity<CustomerResponse> customerDataResponse = customerResourceClient.getCustomerData(evaluationData.getCpf());
            ResponseEntity<List<CardResponse>> cardsResponse  = cardResourceClient.getCardsWithIncomeUpTo(evaluationData.getIncome().longValue());
            List<ApprovedCard> approvedCards = new ArrayList<>();

            if (cardsResponse != null && cardsResponse.getBody() != null && 
                customerDataResponse != null && customerDataResponse.getBody() != null) {
                
                approvedCards = cardsResponse
                                    .getBody()
                                    .stream()
                                    .map(card -> {
                                        return ApprovedCard
                                                .builder()
                                                .card(card.getNome())
                                                .flag(card.getBandeiraCartao())
                                                .approvedLimit(calculateLimit(card.getLimiteBasico(), customerDataResponse.getBody().getIdade()))
                                                .build();
                                    }).toList();
            }

            return EvaluationReturn
                        .builder()
                        .cartoes(approvedCards)
                        .build();

        }catch (FeignException.FeignClientException e){
            throw new ResponseStatusException(HttpStatus.valueOf(e.status()), e.getMessage());
        }
    }

    public CardRequestProtocol requestCardIssuance(CardRequestData cardRequestData){

        try {
            cardIssuanceRequestPublisher.requestCard(cardRequestData);
            var protocol = UUID.randomUUID().toString();

            return CardRequestProtocol
                    .builder()
                    .protocol(protocol)
                    .build();
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro ao realizar a solicitação do cartão!");
        }
    }

    /**
     * Calcula o limite com base em um limite básico e a idade.
     *
     * <p>Este método divide a idade por 10 para obter um fator, e então multiplica esse fator
     * pelo limite básico para calcular o limite final.
     *
     * @param basicLimit O limite básico a ser usado como multiplicador. Deve ser um valor positivo não nulo.
     * @param age A idade usada para calcular o fator. Deve ser um valor positivo não nulo.
     * @return O limite calculado como um BigDecimal.
     */
    private BigDecimal calculateLimit(BigDecimal basicLimit, Integer age) {
        BigDecimal ageBD = BigDecimal.valueOf(age);
        var fator = ageBD.divide(BigDecimal.valueOf(10));

        return fator.multiply(basicLimit);
    }
}
