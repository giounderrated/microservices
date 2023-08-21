package com.example.fraud;

import com.example.clients.fraud.FraudCheckResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/fraud-check/")
public record FraudController(FraudCheckService service) {
    @GetMapping("{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId) {
        boolean isFraudulent = service.isFraudulentCustomer(customerId);
        log.info("Fraud check for customer {}", customerId);
        return new FraudCheckResponse(isFraudulent);
    }
}
