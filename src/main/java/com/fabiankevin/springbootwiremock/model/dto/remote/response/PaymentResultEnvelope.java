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
public class PaymentResultEnvelope {
    private String transactionId;
    private String success;
    private String transactionDate;
}
