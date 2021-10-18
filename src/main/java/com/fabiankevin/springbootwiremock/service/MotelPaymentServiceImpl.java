package com.fabiankevin.springbootwiremock.service;

import com.fabiankevin.springbootwiremock.gateway.CardGateway;
import com.fabiankevin.springbootwiremock.gateway.PaymentGateway;
import com.fabiankevin.springbootwiremock.model.domain.Card;
import com.fabiankevin.springbootwiremock.model.domain.CardBalance;
import com.fabiankevin.springbootwiremock.model.domain.Payment;
import com.fabiankevin.springbootwiremock.model.domain.PaymentResult;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MotelPaymentServiceImpl implements MotelPaymentService {
    private final PaymentGateway paymentGateway;
    private final CardGateway cardGateway;
    @Override
    public PaymentResult pay() {
        String cardNumber = "9131231232";
        Double amount = 5000.0;


        Card card = cardGateway.getCardDetails(cardNumber);

        CardBalance cardBalance = cardGateway.getBalance(cardNumber);
        if(amount > cardBalance.getBalance()){
            throw new RuntimeException("Insufficient balance");
        }
        PaymentResult result;
        try {
            result = paymentGateway.pay(Payment.builder()
                    .amount(amount)
                    .card(card)
                    .build());
        } catch (FeignException e) {
           e.printStackTrace();
            throw new RuntimeException("Something went wrong");
        }

        return result;
    }
}
