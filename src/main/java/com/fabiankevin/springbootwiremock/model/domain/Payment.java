package com.fabiankevin.springbootwiremock.model.domain;

import com.fabiankevin.springbootwiremock.model.dto.remote.request.PaymentBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    private Double amount;
    private Card card;
}
