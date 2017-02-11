package com.richard.axon.core.api.account;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * Created on 2/10/2017.
 */
//@Value
@Aggregate
//@Aggregate(repository = "jpaAccountRepository")
@NoArgsConstructor
public class AccountAggregate {
    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @AggregateIdentifier
    private String accountId;
    private int balance;
    private int overdraftLimit;

    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        apply(new AccountCreatedEvent(command.getAccountId(), command.getOverdraftLimit()));
    }

    @CommandHandler
    public void handle(WithdrawMoneyCommand command) {
        //should no do any state changes here
        if (balance + overdraftLimit >= command.getAmount())
            apply(new MoneyWithdrawnEvent(accountId, command.getTransactionId(), command.getAmount(), balance - command.getAmount()));
        else
            throw new OverdraftLimitExceededException("Exceeded Overdraft.");
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        logger.info("Received New Account Created Event with Account Id: {}", event.getAccountId());
        this.accountId = event.getAccountId();
        this.overdraftLimit = event.getOverdraftLimit();
    }

    @EventSourcingHandler
    public void on(MoneyWithdrawnEvent event) {
        logger.info("New Balance: {} for Account: {}", event.getBalance(), event.getAccountId());
        this.balance = event.getBalance();
    }
}
