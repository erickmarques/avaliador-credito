package com.erickmarques.ms_avaliador_credito.infra.mqueue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.erickmarques.ms_avaliador_credito.domain.CardRequestData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CardIssuanceRequestPublisher {
    
    private final RabbitTemplate rabbitTemplate;
    private final Queue queueCardIssuance;
    private final ObjectMapper mapper;
    
    public void requestCard(CardRequestData cardRequestData) throws JsonProcessingException {
        rabbitTemplate.convertAndSend(queueCardIssuance.getName(), mapper.writeValueAsString(cardRequestData));
    }
}
