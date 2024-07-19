package com.focus.cards.service;

import com.focus.cards.dto.CardsDto;

public interface ICardsService {

    /**
     *
     * @param mobileNumber - Mobile Number
     */
    void createCard(String mobileNumber);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Cards Details based on a given mobileNumber
     */
    CardsDto fetchCard(String mobileNumber);

    /**
     *
     * @param cardsDto - CardsDto Object
     * @return boolean indicating if the update of Card details is successful or not
     */
    boolean updateCard(CardsDto cardsDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Card details is successful or not
     */
    boolean deleteCard(String mobileNumber);
}
