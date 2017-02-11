package com.richard.axon.core.api.transfer;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Created on 2/10/2017.
 */
@Value
public class CompleteMoneyTransferCommand {

    private final String transferId;
}
