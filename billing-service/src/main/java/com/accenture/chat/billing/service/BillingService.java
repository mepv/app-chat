package com.accenture.chat.billing.service;

import com.accenture.chat.billing.dto.ApiResponseDTO;
import com.accenture.chat.billing.dto.BillingDTO;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface BillingService {

    Mono<ServerResponse> saveCurrentQuestionValues(ServerRequest serverRequest);

    Mono<ApiResponseDTO> getBilling();

    void updateBillingValues(BillingDTO billingDTO);
}
