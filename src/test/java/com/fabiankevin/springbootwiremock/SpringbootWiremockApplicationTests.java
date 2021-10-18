package com.fabiankevin.springbootwiremock;

import com.fabiankevin.springbootwiremock.gateway.CardGateway;
import com.fabiankevin.springbootwiremock.model.domain.CardBalance;
import com.fabiankevin.springbootwiremock.model.domain.PaymentResult;
import com.fabiankevin.springbootwiremock.model.dto.remote.request.PaymentBody;
import com.fabiankevin.springbootwiremock.model.dto.remote.response.CardBalanceEnvelope;
import com.fabiankevin.springbootwiremock.model.dto.remote.response.CardEnvelope;
import com.fabiankevin.springbootwiremock.model.dto.remote.response.PaymentResultEnvelope;
import com.fabiankevin.springbootwiremock.remoteservice.CardRemoteService;
import com.fabiankevin.springbootwiremock.service.MotelPaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SpringbootWiremockApplicationTests {

    private static WireMockServer wireMockServer;
    @Autowired
    private CardRemoteService cardRemoteService;

    @Autowired
    private CardGateway cardGateway;

    @Autowired
    private MotelPaymentService motelPaymentService;


    @BeforeAll
    static void setup(){
        System.out.println("@BeforeAll - executes once before all test methods in this class");
        wireMockServer = new WireMockServer(new WireMockConfiguration().port(5000));
        configureFor("localhost", 5000);
        wireMockServer.start();
    }

    @Test
    @DisplayName("Get Balance should return the balance")
    public void getBalance_shouldReturnBalance() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CardBalanceEnvelope cardBalanceEnvelope = new CardBalanceEnvelope();
        cardBalanceEnvelope.setBalance(50000.0);
        cardBalanceEnvelope.setBalanceRequested(LocalDateTime.now().toString());
        String cardEnvelopeJson = objectMapper.writeValueAsString(cardBalanceEnvelope);
        stubFor(get(urlPathMatching("/cards/balance/.*"))
                .willReturn(okJson(cardEnvelopeJson))
        );
        CardBalance balance = cardGateway.getBalance("123243242343");

        assertThat(cardBalanceEnvelope.getBalance()).isEqualTo(balance.getBalance());
        assertThat(cardBalanceEnvelope.getBalanceRequested()).isEqualTo(balance.getBalanceRequested());

    }

    @Test
    public void pay_shouldReturn200AndTransactionId() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();


        ///card details
        CardEnvelope cardEnvelope = new CardEnvelope();;
        cardEnvelope.setCardNumber("9131231232");
        cardEnvelope.setExpiry("12/30");
        String cardJson = objectMapper.writeValueAsString(cardEnvelope);

        stubFor(get(urlPathMatching("/cards/details/.*"))
                .willReturn(okJson(cardJson))
        );

        //card balance
        CardBalanceEnvelope cardBalanceEnvelope = new CardBalanceEnvelope();
        cardBalanceEnvelope.setBalance(50000.0);
        cardBalanceEnvelope.setBalanceRequested(LocalDateTime.now().toString());
        String cardBalanceJson = objectMapper.writeValueAsString(cardBalanceEnvelope);

        stubFor(get(urlPathMatching("/cards/balance/.*"))
                .willReturn(okJson(cardBalanceJson))
        );


        //payment
        PaymentResultEnvelope paymentResultEnvelope = new PaymentResultEnvelope();
        paymentResultEnvelope.setTransactionId("44443");
        paymentResultEnvelope.setSuccess("true");
        paymentResultEnvelope.setTransactionDate(LocalDateTime.now().toString());
        String paymentResultJson = objectMapper.writeValueAsString(paymentResultEnvelope);
        stubFor(post(urlPathMatching("/payment"))
                .withRequestBody(matchingJsonPath("amount"))
                .withRequestBody(matchingJsonPath("card"))
                .withRequestBody(matchingJsonPath("card.cardNumber"))
                .withRequestBody(matchingJsonPath("card.expiry"))
                .willReturn(okJson(paymentResultJson))
        );


        PaymentResult result = motelPaymentService.pay();
        assertThat(result.getSuccess()).isEqualTo("true");
        assertThat(result.getTransactionId()).isEqualTo(paymentResultEnvelope.getTransactionId());
    }


    @Test
    public void pay_whenPaymentReturn500_shouldCatch() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();


        ///card details
        CardEnvelope cardEnvelope = new CardEnvelope();;
        cardEnvelope.setCardNumber("9131231232");
        cardEnvelope.setExpiry("12/30");
        String cardJson = objectMapper.writeValueAsString(cardEnvelope);

        stubFor(get(urlPathMatching("/cards/details/.*"))
                .willReturn(okJson(cardJson))
        );

        //card balance
        CardBalanceEnvelope cardBalanceEnvelope = new CardBalanceEnvelope();
        cardBalanceEnvelope.setBalance(50000.0);
        cardBalanceEnvelope.setBalanceRequested(LocalDateTime.now().toString());
        String cardBalanceJson = objectMapper.writeValueAsString(cardBalanceEnvelope);

        stubFor(get(urlPathMatching("/cards/balance/.*"))
                .willReturn(okJson(cardBalanceJson))
        );


        //payment
        PaymentResultEnvelope paymentResultEnvelope = new PaymentResultEnvelope();
        paymentResultEnvelope.setTransactionId("44443");
        paymentResultEnvelope.setSuccess("true");
        paymentResultEnvelope.setTransactionDate(LocalDateTime.now().toString());
        stubFor(post(urlPathMatching("/payment"))
                .withRequestBody(matchingJsonPath("amount"))
                .withRequestBody(matchingJsonPath("card"))
                .withRequestBody(matchingJsonPath("card.cardNumber"))
                .withRequestBody(matchingJsonPath("card.expiry"))
                .willReturn(serverError())
        );

        assertThrows(RuntimeException.class, () -> {
            PaymentResult result = motelPaymentService.pay();
        }, "Something went wrong");

    }




}
