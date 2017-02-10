package com.richard.axon.core.api.account;

import lombok.Value;
import org.axonframework.commandhandling.model.AggregateIdentifier;

/**
 * Created on 2/10/2017.
 */
@Value
public class CreateAccountCommand {

    private final String accountId;
    private final int overdraftLimit;
}
