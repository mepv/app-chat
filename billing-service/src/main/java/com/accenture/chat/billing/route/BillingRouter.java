package com.accenture.chat.billing.route;

import com.accenture.chat.billing.service.BillingServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BillingRouter {

    @Bean
    public RouterFunction<ServerResponse> billingRoutes(BillingServiceImpl billingService) {
        return route()
                .nest(path("/api/v1/billing"), builder -> builder
                        .POST("", billingService::saveCurrentQuestionValues))
                .build();
    }
}
