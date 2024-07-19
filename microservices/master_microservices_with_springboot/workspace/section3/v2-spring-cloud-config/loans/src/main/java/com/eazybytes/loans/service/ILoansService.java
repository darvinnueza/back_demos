package com.eazybytes.loans.service;

import com.eazybytes.loans.dto.LoanDto;

public interface ILoansService {

    /**
     *
     * @param mobileNumber
     */
    void createLoan(String mobileNumber);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Loans Details based on a given mobileNumber
     */
    LoanDto fetchLoan(String mobileNumber);

    /**
     *
     * @param loansDto - LoansDto Object
     * @return boolean indicating if the update of Loan details is successful or not
     */
    boolean updateLoan(LoanDto loansDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Loan details is successful or not
     */
    boolean deleteLoan(String mobileNumber);
}
