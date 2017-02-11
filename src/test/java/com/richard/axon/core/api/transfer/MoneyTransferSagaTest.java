package com.richard.axon.core.api.transfer;

import com.richard.axon.core.api.account.DepositMoneyCommand;
import com.richard.axon.core.api.account.MoneyDepositedEvent;
import com.richard.axon.core.api.account.MoneyWithdrawnEvent;
import com.richard.axon.core.api.account.WithdrawMoneyCommand;
import org.axonframework.test.saga.SagaTestFixture;
import org.junit.Before;
import org.junit.Test;

/**
 * Created on 2/10/2017.
 */
public class MoneyTransferSagaTest {
    private SagaTestFixture<MoneyTransferSaga> fixture;

    @Before
    public void setup() {
        fixture = new SagaTestFixture<>(MoneyTransferSaga.class);
    }

    @Test
    public void testMoneyTransferRequest() {
        fixture.givenNoPriorActivity()
               .whenPublishingA(new MoneyTransferRequestedEvent("t-1", "acc-1", "acc-2", 100))
               .expectActiveSagas(1)
               .expectDispatchedCommands(new WithdrawMoneyCommand("acc-1", "t-1", 100));
    }

    @Test
    public void testMoneyTransferOverDrawnRequest() {
        fixture.givenNoPriorActivity()
               .whenPublishingA(new MoneyTransferRequestedEvent("t-1", "acc-1", "acc-2", 2000))
               .expectActiveSagas(1)
               .expectDispatchedCommands(new WithdrawMoneyCommand("acc-1", "t-1", 100));
    }

    @Test
    public void testDepositMoneyAfterWithdrawn() {
        fixture.givenAPublished(new MoneyTransferRequestedEvent("t-1", "acc-1", "acc-2", 100))
               .whenPublishingA(new MoneyWithdrawnEvent("acc-1", "t-1", 100, 500))
               .expectDispatchedCommands(new DepositMoneyCommand("acc-2", "t-1", 100));
    }

    @Test
    public void testTransferCompleteAfterDeposit() throws Exception {
        fixture.givenAPublished(new MoneyTransferRequestedEvent("t-1", "acc-1", "acc-2", 100))
               .andThenAPublished(new MoneyWithdrawnEvent("acc-1", "t-1", 100, 500))
               .whenPublishingA(new MoneyDepositedEvent("acc-2", "t-1", 100, 400))
               .expectDispatchedCommands(new CompleteMoneyTransferCommand("t-1"));
    }

    @Test
    public void testSagaEndsTransferCompleteAfterDeposit() throws Exception {
        fixture.givenAPublished(new MoneyTransferRequestedEvent("t-1", "acc-1", "acc-2", 100))
               .andThenAPublished(new MoneyWithdrawnEvent("acc-1", "t-1", 100, 500))
               .andThenAPublished(new MoneyDepositedEvent("acc-2", "t-1", 100, 400))
               .whenPublishingA(new MoneyTransferCompletedEvent("t-1"))
               .expectNoDispatchedCommands().expectActiveSagas(0);
    }
}