package com.fabiankevin.springbootwiremock.remoteservice;

import com.fabiankevin.springbootwiremock.model.domain.Payment;
import com.fabiankevin.springbootwiremock.model.dto.remote.request.PaymentBody;
import com.fabiankevin.springbootwiremock.model.dto.remote.response.PaymentResultEnvelope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment", url = "localhost:5000/payment")
public interface PaymentRemoteService {

    @PostMapping
    PaymentResultEnvelope pay(PaymentBody paymentBody);
}
