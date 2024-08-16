package com.focus.accounts.service.client.impl;

import com.focus.accounts.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.focus.accounts.service.client.ICardsFeignClient;

@Component
public class CardsFallback implements ICardsFeignClient {

    @Override
    public ResponseEntity<CardsDto> fetchCardDetails(String correlationId, String mobileNumber) {
        return null;
    }
}