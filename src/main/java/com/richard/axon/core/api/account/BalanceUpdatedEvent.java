package com.richard.axon.core.api.account;

import lombok.Data;
import lombok.Value;

/**
 * Created on 2/10/2017.
 */
@Data
public abstract class BalanceUpdatedEvent {
    private String accountId;
    private  int balance;
}
