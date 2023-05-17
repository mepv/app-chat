package com.accenture.chat.billing.service;

import com.accenture.chat.billing.dto.ApiResponseDTO;
import com.accenture.chat.billing.dto.BillingDTO;
import com.accenture.chat.billing.repository.BillingRepository;
import com.accenture.chat.billing.repository.CountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class BillingServiceImpl implements BillingService {

    private static final Logger log = LoggerFactory.getLogger(BillingServiceImpl.class);
    private static final String QUESTION_VALUE = "question-value";
    public static final String CURRENT_CHARGE = "current-charge";
    private static final String RATE = "rate";
    private static final String COUNT = "count";

    private final BillingRepository billingRepository;
    private final CountRepository countRepository;

    public BillingServiceImpl(BillingRepository billingRepository, CountRepository countRepository) {
        this.billingRepository = billingRepository;
        this.countRepository = countRepository;
    }

    @Override
    public Mono<ServerResponse> saveCurrentQuestionValues(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(BillingDTO.class)
                .flatMap(billingDTO -> {
                    BigDecimal questionValue = BigDecimal.valueOf(billingRepository.findValueByKey(QUESTION_VALUE));
                    countRepository.saveValue(COUNT, billingDTO.getCount());
                    int count = countRepository.findValueByKey(COUNT);
                    double rate = billingRepository.findValueByKey(RATE);
                    if (count > 3) {
                        BigDecimal inflationFactor = BigDecimal.valueOf(rate).add(questionValue);
                        BigDecimal charge = BigDecimal.valueOf((count - 3)).multiply(inflationFactor);
                        billingRepository.saveValue(CURRENT_CHARGE, charge.doubleValue());
                    }
                    log.info("count billing service: {}", count);
                    return ServerResponse.status(HttpStatus.OK).build();
                });
    }

    @Override
    public Mono<ApiResponseDTO> getBilling() {
        int count = countRepository.findValueByKey(COUNT);
        double charge = billingRepository.findValueByKey(CURRENT_CHARGE);
        String s = String.format("El total por las %s preguntas es: %s", count, charge);
        ApiResponseDTO response = new ApiResponseDTO();
        response.setTotal(s);
        return Mono.just(response);
    }

    @Override
    public void updateBillingValues(BillingDTO billingDTO) {
        if (billingDTO.getQuestionValue() > 0.0) {
            billingRepository.saveValue(QUESTION_VALUE, billingDTO.getQuestionValue());
        }

        if (billingDTO.getRate() > 0.0) {
            billingRepository.saveValue(RATE, billingDTO.getRate());
        }
    }
}
