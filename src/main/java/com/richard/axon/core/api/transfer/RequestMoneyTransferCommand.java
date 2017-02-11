package com.richard.axon.core.api.transfer;

import com.richard.axon.core.api.account.AccountAggregate;
import lombok.Value;

/**
 * Created on 2/10/2017.
 */
@Value
public class RequestMoneyTransferCommand {
    private final String transferId;
    private final String sourceAccount;
    private final String targetAccount;
    private final int amount;
}
