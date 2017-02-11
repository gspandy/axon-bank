package com.richard.axon.core.api.transfer;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;

/**
 * Created on 2/10/2017.
 */
@NoArgsConstructor
@Aggregate
public class MoneyTransfer {

    @AggregateIdentifier
    private String transferId;

    @CommandHandler
    public MoneyTransfer(RequestMoneyTransferCommand command) {
        apply(new MoneyTransferRequestedEvent(command.getTransferId(), command.getSourceAccount(), command.getTargetAccount(),
                command.getAmount()));
    }

    @CommandHandler
    public void handle(CompleteMoneyTransferCommand command) {
        apply(new MoneyTransferCompletedEvent(command.getTransferId()));
    }

    @CommandHandler
    public void handle(CancelMoneyTransferCommand command) {
        apply(new MoneyTransferCancelledEvent(command.getTransferId()));
    }

    @EventSourcingHandler
    public void on(MoneyTransferRequestedEvent event) {
        this.transferId = event.getTransferId();
    }

    @EventSourcingHandler
    public void on(MoneyTransferCompletedEvent event) {
        markDeleted();
    }
}
