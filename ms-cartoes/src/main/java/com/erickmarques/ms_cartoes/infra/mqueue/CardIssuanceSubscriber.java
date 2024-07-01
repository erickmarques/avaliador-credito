package com.erickmarques.ms_cartoes.infra.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.erickmarques.ms_cartoes.application.CardIssuanceService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CardIssuanceSubscriber {

    private final CardIssuanceService cardIssuanceService;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receiveIssuanceRequest(@Payload String payload){
        cardIssuanceService.processIssuanceRequest(payload);
    }
}
