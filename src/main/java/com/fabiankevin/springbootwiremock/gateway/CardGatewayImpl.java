package com.fabiankevin.springbootwiremock.gateway;

import com.fabiankevin.springbootwiremock.model.domain.Card;
import com.fabiankevin.springbootwiremock.model.domain.CardBalance;
import com.fabiankevin.springbootwiremock.model.dto.remote.response.CardBalanceEnvelope;
import com.fabiankevin.springbootwiremock.model.dto.remote.response.CardEnvelope;
import com.fabiankevin.springbootwiremock.remoteservice.CardRemoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardGatewayImpl implements CardGateway {
    private final CardRemoteService cardRemoteService;
    @Override
    public CardBalance getBalance(String cardNumber) {
        CardBalanceEnvelope envelope = cardRemoteService.getBalance(cardNumber);
        return CardBalance.builder()
                .balance(envelope.getBalance())
                .balanceRequested(envelope.getBalanceRequested())
                .build();
    }

    @Override
    public Card getCardDetails(String cardNumber) {
        CardEnvelope cardEnvelope = cardRemoteService.getCardDetail(cardNumber);
        return Card.builder()
                .cardNumber(cardEnvelope.getCardNumber())
                .expiry(cardEnvelope.getExpiry())
                .build();
    }
}
