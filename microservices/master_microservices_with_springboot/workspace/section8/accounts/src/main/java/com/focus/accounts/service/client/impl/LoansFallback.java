package com.focus.accounts.service.client.impl;

import com.focus.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.focus.accounts.service.client.ILoansFeignClient;

@Component
public class LoansFallback implements ILoansFeignClient {
    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String correlationId, String mobileNumber) {
        return null;
    }
}