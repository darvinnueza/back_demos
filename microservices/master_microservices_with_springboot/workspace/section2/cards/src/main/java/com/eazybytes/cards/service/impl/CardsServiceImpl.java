package com.eazybytes.cards.service.impl;

import java.util.Random;
import java.util.Optional;
import lombok.AllArgsConstructor;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.mapper.CardMapper;
import org.springframework.stereotype.Service;
import com.eazybytes.cards.service.ICardsService;
import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.repository.CardsRepository;
import com.eazybytes.cards.exception.ResourceNotFoundException;
import com.eazybytes.cards.exception.CardAlreadyExistsException;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalLoans = cardsRepository.findByMobileNumber(mobileNumber);
        if (optionalLoans.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with give mobileNumber " + mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Card", "MobileNumber", mobileNumber));
        return CardMapper.mapToLoansDto(cards, new CardsDto());
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(() -> new ResourceNotFoundException("Card", "MobileNumber", cardsDto.getCardNumber()));
        CardMapper.mapToLoans(cardsDto, cards);
        cardsRepository.save(cards);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Card", "MobileNumber", mobileNumber));
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);

        return newCard;
    }
}
