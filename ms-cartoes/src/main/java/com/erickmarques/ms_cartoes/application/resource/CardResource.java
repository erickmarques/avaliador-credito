package com.erickmarques.ms_cartoes.application.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erickmarques.ms_cartoes.application.representation.CardSaveRequest;
import com.erickmarques.ms_cartoes.application.representation.CardSaveResponse;
import com.erickmarques.ms_cartoes.application.service.CardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cartoes")
@RequiredArgsConstructor
public class CardResource {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardSaveResponse> criarCartao(@Valid @RequestBody CardSaveRequest cardSaveRequest){
        CardSaveResponse cardSaveResponse = cardService.createCard(cardSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cardSaveResponse);
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<CardSaveResponse>> pesquisarCartoesComRendaAte(@RequestParam("renda") Long renda){
        List<CardSaveResponse> cards = cardService.findByIncomeLessThanEqualOrderByIncomeDesc(renda);
        return ResponseEntity.ok(cards);
    }
}
