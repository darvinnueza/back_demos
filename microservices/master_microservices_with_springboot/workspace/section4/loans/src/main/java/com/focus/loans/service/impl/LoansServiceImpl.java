package com.focus.loans.service.impl;

import com.focus.loans.constants.LoansConstants;
import com.focus.loans.entity.Loans;
import com.focus.loans.exception.LoanAlreadyExistsException;
import com.focus.loans.exception.ResourceNotFoundException;
import com.focus.loans.mapper.LoanMapper;
import com.focus.loans.repository.LoansRepository;
import com.focus.loans.dto.LoanDto;
import com.focus.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if (optionalLoans.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with give mobileNumber " + mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    @Override
    public LoanDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Loan", "MobileNumber", mobileNumber));
        return LoanMapper.mapToLoansDto(loans, new LoanDto());
    }

    @Override
    public boolean updateLoan(LoanDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(() -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoanMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Loan", "MobileNumber", mobileNumber));
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);

        return newLoan;
    }
}
