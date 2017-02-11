package com.richard.axon.event.handlers;

import com.richard.axon.core.api.account.BalanceUpdatedEvent;
import com.richard.axon.models.AccountBalance;
import com.richard.axon.repository.AccountBalanceRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created on 2/10/2017.
 */
@Component
public class AccountBalanceHandler {

    @Inject
    private AccountBalanceRepository accountBalanceRepository;

    @EventHandler
    public void on(BalanceUpdatedEvent event) {
        accountBalanceRepository.save(new AccountBalance(event.getAccountId(), event.getBalance()));
    }
}
