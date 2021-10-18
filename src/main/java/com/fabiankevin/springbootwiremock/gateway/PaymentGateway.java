package com.fabiankevin.springbootwiremock.gateway;

import com.fabiankevin.springbootwiremock.model.domain.Payment;
import com.fabiankevin.springbootwiremock.model.domain.PaymentResult;

public interface PaymentGateway {
    PaymentResult pay(Payment payment);
}
