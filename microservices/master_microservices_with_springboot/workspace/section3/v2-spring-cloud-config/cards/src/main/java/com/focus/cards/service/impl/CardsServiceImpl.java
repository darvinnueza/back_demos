package com.focus.cards.service.impl;

import com.focus.cards.constants.CardsConstants;
import com.focus.cards.entity.Cards;
import com.focus.cards.exception.CardAlreadyExistsException;
import com.focus.cards.exception.ResourceNotFoundException;
import com.focus.cards.mapper.CardMapper;
import com.focus.cards.repository.CardsRepository;
import com.focus.cards.dto.CardsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.focus.cards.service.ICardsService;

import java.util.Optional;
import java.util.Random;

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
