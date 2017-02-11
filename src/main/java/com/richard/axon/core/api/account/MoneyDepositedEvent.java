package com.richard.axon.core.api.account;

import lombok.Value;

/**
 * Created on 2/10/2017.
 */
@Value
public class MoneyDepositedEvent extends BalanceUpdatedEvent {
    private final String accountId;
    private final String transactionId;
    private final int amount;
    private final int balance;
}
