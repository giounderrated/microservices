package com.example.fraud;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
@Service
public record FraudCheckService(FraudCheckHistoryRepository repository) {
    public boolean isFraudulentCustomer(Integer customerId){
        repository.saveAndFlush(
                FraudCheckHistory.builder()
                        .customerId(customerId)
                        .isFraudster(false)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        return false;
    }

}
