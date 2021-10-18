package com.fabiankevin.springbootwiremock.model.dto.remote.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardBalanceEnvelope {
    private Double balance;
    private String balanceRequested;
}
