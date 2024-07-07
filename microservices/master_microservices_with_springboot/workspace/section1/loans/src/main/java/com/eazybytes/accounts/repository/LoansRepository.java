package com.eazybytes.accounts.repository;

import java.util.Optional;
import com.eazybytes.accounts.entity.Loans;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LoansRepository extends JpaRepository<Loans, Long> {

    Optional<Loans> findByMobileNumber(String mobileNumber);

    Optional<Loans> findByLoanNumber(String mobileNumber);
}
