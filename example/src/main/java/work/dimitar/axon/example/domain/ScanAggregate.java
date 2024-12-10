package work.dimitar.axon.example.domain;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import work.dimitar.axon.example.api.commands.Commands;
import work.dimitar.axon.example.api.events.Events;

import java.util.HashMap;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * Aggregate example.
 */

@Slf4j
@NoArgsConstructor
@Aggregate
public class ScanAggregate {

    @AggregateIdentifier
    private UUID scanId;

    @CommandHandler
    public ScanAggregate(Commands.ReceiveScanCommand command) {
        log.info("ScanAggregate: scan received command: [{}]", command);

        apply(Events.ScanReceivedEvent.builder()
                .scanId(command.scanId)
                .hardwareId(command.hardwareId)
                .scanTime(command.scanTime)
                .build());
    }

    @CommandHandler
    public void on(Commands.OtherCommand command) {
        log.info("ScanAggregate: Other command: [{}]", command);

        apply(Events.OtherEvent.builder()
                .scanId(command.scanId)
                .timestamp(command.timestamp)
                .build());
    }

    @EventSourcingHandler
    public void on(Events.ScanReceivedEvent event) {
        log.info("ScanAggregate: Scan received event: [{}]", event);
        this.scanId = event.scanId;
    }
}
