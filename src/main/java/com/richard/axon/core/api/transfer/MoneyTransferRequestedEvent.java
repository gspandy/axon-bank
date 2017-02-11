package com.richard.axon.core.api.transfer;

import com.richard.axon.core.api.account.AccountAggregate;
import lombok.Value;

/**
 * Created on 2/10/2017.
 */
@Value
public class MoneyTransferRequestedEvent {
    private final String transferId;
    private final String sourceAccountId;
    private final String targetAccountId;
    private final int amount;
}
