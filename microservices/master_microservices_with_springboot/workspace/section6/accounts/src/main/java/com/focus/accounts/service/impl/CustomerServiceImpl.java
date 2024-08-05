package com.focus.accounts.service.impl;

import lombok.AllArgsConstructor;
import com.focus.accounts.dto.CardsDto;
import com.focus.accounts.dto.LoansDto;
import com.focus.accounts.dto.AccountsDto;
import com.focus.accounts.entity.Accounts;
import com.focus.accounts.entity.Customer;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import com.focus.accounts.mapper.AccountMapper;
import com.focus.accounts.mapper.CustomerMapper;
import com.focus.accounts.dto.CustomerDetailsDto;
import com.focus.accounts.service.ICustomerService;
import com.focus.accounts.repository.AccountsRepository;
import com.focus.accounts.repository.CustomerRepository;
import com.focus.accounts.service.client.CardsFeignClient;
import com.focus.accounts.service.client.LoansFeignClient;
import com.focus.accounts.exception.ResourceNotFoundException;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString()));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}