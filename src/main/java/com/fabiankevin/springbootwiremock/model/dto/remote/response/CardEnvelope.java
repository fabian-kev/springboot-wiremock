package com.fabiankevin.springbootwiremock.model.dto.remote.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardEnvelope {
    private String cardNumber;
    private String expiry;
}