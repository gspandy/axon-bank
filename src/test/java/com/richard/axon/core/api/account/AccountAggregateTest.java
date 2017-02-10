package com.richard.axon.core.api.account;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Before;
import org.junit.Test;

/**
 * Created on 2/10/2017.
 */
public class AccountAggregateTest {

    private AggregateTestFixture<AccountAggregate> fixture;

    @Before
    public void setup() {
        fixture = new AggregateTestFixture<>(AccountAggregate.class);
    }

    @Test
    public void testCreateAccount() {
        fixture.givenNoPriorActivity()
               .when(new CreateAccountCommand("1234", 1000))
               .expectEvents(new AccountCreatedEvent("1234", 1000));
    }

    @Test
    public void testWithdrawReasonableAmount() {
        fixture.given(new AccountCreatedEvent("1234", 1000))
               .when(new WithdrawMoneyCommand("1234", 40))
               .expectEvents(new MoneyWithdrawnEvent("1234", 40, -40));
    }

    @Test
    public void testWithdrawCrazyAmount() {
        fixture.given(new AccountCreatedEvent("1234", 1000))
               .when(new WithdrawMoneyCommand("1234", 1001))
               .expectNoEvents()
               .expectException(OverdraftLimitExceededException.class);
    }

    @Test
    public void withdrawTwice() {
        fixture.given(new AccountCreatedEvent("1234", 1000), new MoneyWithdrawnEvent("1234", 999, -999))
               .when(new WithdrawMoneyCommand("1234", 2))
               .expectNoEvents()
               .expectException(OverdraftLimitExceededException.class);
    }
}