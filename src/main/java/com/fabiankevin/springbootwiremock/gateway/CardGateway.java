package com.fabiankevin.springbootwiremock.gateway;

import com.fabiankevin.springbootwiremock.model.domain.Card;
import com.fabiankevin.springbootwiremock.model.domain.CardBalance;

public interface CardGateway {
    CardBalance getBalance(String cardNumber);
    Card getCardDetails(String cardNumber);
}
