package com.fabiankevin.springbootwiremock.gateway;

import com.fabiankevin.springbootwiremock.model.domain.Payment;
import com.fabiankevin.springbootwiremock.model.domain.PaymentResult;
import com.fabiankevin.springbootwiremock.model.dto.remote.request.PaymentBody;
import com.fabiankevin.springbootwiremock.model.dto.remote.response.PaymentResultEnvelope;
import com.fabiankevin.springbootwiremock.remoteservice.PaymentRemoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PaymentGatewayImpl implements PaymentGateway {
    private final PaymentRemoteService paymentRemoteService;

    @Override
    public PaymentResult pay(Payment payment) {
        PaymentBody paymentBody = PaymentBody.builder()
                .amount(payment.getAmount())
                .card(PaymentBody.Card.builder()
                        .cardNumber(payment.getCard().getCardNumber())
                        .expiry(payment.getCard().getExpiry())
                        .build())
                .build();

        PaymentResultEnvelope resultEnvelope = paymentRemoteService.pay(paymentBody);
        ;
        return PaymentResult.builder()
                .success(resultEnvelope.getSuccess())
                .transactionDate(resultEnvelope.getTransactionDate())
                .transactionId(resultEnvelope.getTransactionId())
                .build();
    }
}
