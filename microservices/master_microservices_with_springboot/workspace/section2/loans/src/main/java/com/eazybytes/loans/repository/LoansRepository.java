package com.eazybytes.loans.repository;

import java.util.Optional;
import com.eazybytes.loans.entity.Loans;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LoansRepository extends JpaRepository<Loans, Long> {

    Optional<Loans> findByMobileNumber(String mobileNumber);

    Optional<Loans> findByLoanNumber(String mobileNumber);
}
