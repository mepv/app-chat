package com.accenture.chat.billing.controller;

import com.accenture.chat.billing.dto.ApiResponseDTO;
import com.accenture.chat.billing.dto.BillingDTO;
import com.accenture.chat.billing.service.BillingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping
    public Mono<ApiResponseDTO> getBilling() {
        return billingService.getBilling();
    }

    @PostMapping("/update-values")
    public void updateBillingValues(@RequestBody BillingDTO billingDTO) {
        billingService.updateBillingValues(billingDTO);
    }
}
