package com.richard.axon.core.api.account;

/**
 * Created on 2/10/2017.
 */
public class OverdraftLimitExceededException extends RuntimeException {
    public OverdraftLimitExceededException(String message) {
        super(message);
    }
}
