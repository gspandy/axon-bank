package com.richard.axon.core.api.transfer;

import lombok.Value;

/**
 * Created on 2/10/2017.
 */
@Value
public class MoneyTransferCompletedEvent {
    private final String transferId;
}
