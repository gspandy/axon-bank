package com.richard.axon.core.api.account;

import lombok.Value;

/**
 * Created on 2/10/2017.
 */
@Value
public class AccountCreatedEvent {
    private final String accountId;
    private final int overdraftLimit;
}
