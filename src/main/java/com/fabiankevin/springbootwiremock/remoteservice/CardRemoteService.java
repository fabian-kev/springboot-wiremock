package com.fabiankevin.springbootwiremock.remoteservice;

import com.fabiankevin.springbootwiremock.model.dto.remote.response.CardBalanceEnvelope;
import com.fabiankevin.springbootwiremock.model.dto.remote.response.CardEnvelope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "card", url = "localhost:5000/cards")
public interface CardRemoteService {
    @GetMapping("/balance/{cardNumber}")
    CardBalanceEnvelope getBalance(@PathVariable("cardNumber") String cardNumber);

    @GetMapping("/details/{cardNumber}")
    CardEnvelope getCardDetail(@PathVariable("cardNumber") String cardNumber);
}
