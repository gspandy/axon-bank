package com.richard.axon.core.api.transfer;

import com.richard.axon.core.api.account.*;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.SagaLifecycle;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import javax.inject.Inject;
import java.util.UUID;

/**
 * Created on 2/10/2017.
 */
@Saga
public class MoneyTransferSaga {

    @Inject
    private transient CommandGateway commandGateway;

    private String targetAccount;
    private String transactionId;
    private String transferId;

    public MoneyTransferSaga() {
    }

    public MoneyTransferSaga(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "transferId")
    public void on(MoneyTransferRequestedEvent event) {
        this.targetAccount = event.getTargetAccountId();
        this.transactionId = UUID.randomUUID().toString();
        this.transferId = event.getTransferId();

        SagaLifecycle.associateWith("transactionId", transactionId);
       /* try {
            commandGateway.sendAndWait(new WithdrawMoneyCommand(event.getSourceAccountId(), event.getTransferId(), event.getAmount()));
        }catch (CommandExecutionException ex){
            if(OverdraftLimitExceededException.class.isInstance(ex)){
                commandGateway.send(new CancelMoneyTransferCommand(event.getTransferId()));
            }
        }*/

       commandGateway.send(new WithdrawMoneyCommand(event.getSourceAccountId(), transactionId, event.getAmount()), new CommandCallback<WithdrawMoneyCommand, Object>() {
           @Override
           public void onSuccess(CommandMessage<? extends WithdrawMoneyCommand> commandMessage, Object result) {

           }

           @Override
           public void onFailure(CommandMessage<? extends WithdrawMoneyCommand> commandMessage, Throwable cause) {
               commandGateway.send(new CancelMoneyTransferCommand(event.getTransferId()));
           }
       });
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(MoneyWithdrawnEvent event) {
        commandGateway.send(new DepositMoneyCommand(this.targetAccount, transactionId, event.getAmount()));
    }
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(MoneyDepositedEvent event) {
        commandGateway.send(new CompleteMoneyTransferCommand(event.getTransactionId()));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transferId")
    public void on(MoneyTransferCompletedEvent event) {
        SagaLifecycle.end();
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transferId")
    public void on(MoneyTransferCancelledEvent event) {
        SagaLifecycle.end();
    }
}
