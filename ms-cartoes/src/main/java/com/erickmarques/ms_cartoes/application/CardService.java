package com.erickmarques.ms_cartoes.application;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

