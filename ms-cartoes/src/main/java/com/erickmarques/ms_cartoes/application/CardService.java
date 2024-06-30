package com.erickmarques.ms_cartoes.application;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.erickmarques.ms_cartoes.application.mapper.CardMapper;
import com.erickmarques.ms_cartoes.application.representation.CardSaveRequest;
import com.erickmarques.ms_cartoes.application.representation.CardSaveResponse;
import com.erickmarques.ms_cartoes.domain.Card;
import com.erickmarques.ms_cartoes.infra.CardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService {
    
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    @Transactional
    public CardSaveResponse createCard(CardSaveRequest cardSaveRequest){

        if (cardRepository.findByName(cardSaveRequest.getNome()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um cartão cadastrado com este nome!");
        }

        Card card = cardMapper.toEntity(cardSaveRequest);
        Card saveCard = cardRepository.save(card);
        return cardMapper.toDto(saveCard);
    }

    @Transactional(readOnly = true)
    public List<CardSaveResponse> findByIncomeLessThanEqualOrderByIncomeDesc(Long renda){
        var rendaBigDecimal = BigDecimal.valueOf(renda);
        
        return cardRepository
                .findByIncomeLessThanEqualOrderByIncomeDesc(rendaBigDecimal)
                .stream()
                .map(card -> cardMapper.toDto(card))
                .toList();
    }
}

