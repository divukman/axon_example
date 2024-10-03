package work.dimitar.axon.example.domain;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import work.dimitar.axon.example.api.commands.Commands;
import work.dimitar.axon.example.api.events.Events;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ScanAggregateTest {

    private AggregateTestFixture<ScanAggregate> fixture;

    private Commands.ReceiveScanCommand scanCommand;
    private Commands.OtherCommand otherCommand;

    private Events.ScanReceivedEvent scanReceivedEvent;
    private Events.OtherEvent otherEvent;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(ScanAggregate.class);

        scanCommand = Commands.ReceiveScanCommand.builder()
                .scanId(UUID.randomUUID())
                .scanTime(Instant.now())
                .hardwareId("abcdef1234")
                .build();

        scanReceivedEvent = Events.ScanReceivedEvent.builder()
                .scanId(scanCommand.scanId)
                .scanTime(scanCommand.scanTime)
                .hardwareId(scanCommand.hardwareId)
                .build();

        otherCommand = Commands.OtherCommand.builder()
                .scanId(scanCommand.scanId)
                .timestamp(Instant.now())
                .build();

        otherEvent = Events.OtherEvent.builder()
                .scanId(otherCommand.scanId)
                .timestamp(otherCommand.timestamp)
                .build();
    }

    @Test
    public void shouldReceiveScan() {
        fixture.givenNoPriorActivity()
                .when(scanCommand)
                .expectEvents(scanReceivedEvent);
    }

    @Test
    public void shouldInspectScan() {
        fixture.given(scanReceivedEvent)
                .when(otherCommand)
                .expectEvents(otherEvent);
    }

    @Test
    public void shouldNotRunSameCommand() {
        fixture.given(scanReceivedEvent)
                .when(scanCommand)
                .expectNoEvents();
    }

}