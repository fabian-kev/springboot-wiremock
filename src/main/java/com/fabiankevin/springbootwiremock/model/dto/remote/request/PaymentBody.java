package com.fabiankevin.springbootwiremock.model.dto.remote.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentBody {
    private Double amount;
    private Card card;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Card {
        private String cardNumber;
        private String expiry;
    }

}
