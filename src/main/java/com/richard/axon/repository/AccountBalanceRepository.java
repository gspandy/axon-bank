package com.richard.axon.repository;

import com.richard.axon.models.AccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 2/10/2017.
 */
@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, String> {
}
