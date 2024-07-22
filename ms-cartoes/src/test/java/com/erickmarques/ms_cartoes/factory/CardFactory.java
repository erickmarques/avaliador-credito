package com.erickmarques.ms_cartoes.factory;

import java.math.BigDecimal;
import java.util.List;



import com.erickmarques.ms_cartoes.application.representation.CardSaveRequest;
import com.erickmarques.ms_cartoes.application.representation.CardSaveResponse;
import com.erickmarques.ms_cartoes.domain.Card;
import com.erickmarques.ms_cartoes.domain.CardFlag;

public class CardFactory {

    public static final Long ID                = 1L;
    public static final String NAME            = "BLACK";
    public static final CardFlag MASTERCARD    = CardFlag.MASTERCARD;
    public static final BigDecimal INCOME      = BigDecimal.valueOf(6000);
    public static final BigDecimal BASIC_LIMIT = BigDecimal.valueOf(1800);

    public static Card createCardDefault(){
        return new Card(ID, NAME, MASTERCARD, INCOME, BASIC_LIMIT);
    }

    public static List<Card> createCardListDefault(){
        return List.of(new Card(ID, NAME, MASTERCARD, INCOME, BASIC_LIMIT),
                       new Card(2L, "GOLDEN", CardFlag.VISA, BigDecimal.valueOf(10000), BigDecimal.valueOf(1500)),
                       new Card(3L, "SILVER", CardFlag.ELO, BigDecimal.valueOf(3000), BigDecimal.valueOf(900)));
    }

    public static CardSaveRequest createCardSaveRequestDefault() {
        return CardSaveRequest
                .builder()
                .nome(NAME)
                .bandeiraCartao(MASTERCARD.toString())
                .renda(INCOME)
                .limiteBasico(BASIC_LIMIT)
                .build();
    }

    public static CardSaveResponse createCardSaveResponseDefault() {
        return CardSaveResponse
                .builder()
                .id(ID)
                .nome(NAME)
                .bandeiraCartao(MASTERCARD)
                .renda(INCOME)
                .limiteBasico(BASIC_LIMIT)
                .build();
    }

    public static List<CardSaveResponse> createCardSaveResponseListDefault() {
        return List.of(
                    CardSaveResponse
                        .builder()
                        .id(ID)
                        .nome(NAME)
                        .bandeiraCartao(MASTERCARD)
                        .renda(INCOME)
                        .limiteBasico(BASIC_LIMIT)
                        .build(),

                    CardSaveResponse.builder()
                        .id(2L)
                        .nome("GOLDEN")
                        .bandeiraCartao(CardFlag.VISA)
                        .renda(BigDecimal.valueOf(10000))
                        .limiteBasico(BigDecimal.valueOf(1500))
                        .build(),

                    CardSaveResponse.builder()
                        .id(3L)
                        .nome("SILVER")
                        .bandeiraCartao(CardFlag.ELO)
                        .renda(BigDecimal.valueOf(3000))
                        .limiteBasico(BigDecimal.valueOf(900))
                        .build()
                );
    }

}
