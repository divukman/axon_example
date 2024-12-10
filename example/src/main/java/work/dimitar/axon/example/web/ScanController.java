package work.dimitar.axon.example.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.command.AggregateStreamCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import work.dimitar.axon.example.api.commands.Commands;
import work.dimitar.axon.example.api.commands.Commands.ReceiveScanCommand;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/scans")
public class ScanController {

    public static final UUID BREAK_SCAN_ID = UUID.randomUUID();

    private final CommandGateway commandGateway;


    @Autowired
    public ScanController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public String createScan(@RequestParam String hardwareId) {
        UUID scanId = UUID.randomUUID();
        Instant scanTime = Instant.now();

        ReceiveScanCommand command = ReceiveScanCommand.builder()
                .scanId(scanId)
                .hardwareId(hardwareId)
                .scanTime(scanTime)
                .build();

        // Send the command on the Axon command bus
        commandGateway.send(command);

        return "Scan created with ID: " + scanId;
    }

    @PostMapping("/break")
    public String breakAggregateCreation(@RequestParam String hardwareId) {
        final UUID scanId = ScanController.BREAK_SCAN_ID;
        Instant scanTime = Instant.now();

        ReceiveScanCommand command = ReceiveScanCommand.builder()
                .scanId(scanId)
                .hardwareId(hardwareId)
                .scanTime(scanTime)
                .build();

        // Send the command on the Axon command bus
            try {
                commandGateway.sendAndWait(command);
            } catch (AggregateStreamCreationException ex) {
                // Handle duplicate aggregate identifier case
                System.err.println("Duplicate aggregate identifier: " + ex.getMessage());
                // Perform any additional logic, like logging or sending a response
            } catch (Exception ex) {
                // Catch any other exceptions
                System.err.println("Unexpected error: " + ex.getMessage());
            }


        return "Scan created with ID: " + scanId;
    }

    @PostMapping("/break2")
    public String breakAggregateCreation2(@RequestParam String hardwareId) {
        final UUID scanId = ScanController.BREAK_SCAN_ID;
        Instant scanTime = Instant.now();

        ReceiveScanCommand command = ReceiveScanCommand.builder()
                .scanId(scanId)
                .hardwareId(hardwareId)
                .scanTime(scanTime)
                .build();

        // Send the command on the Axon command bus
        try {
            commandGateway.send(command);
        } catch (AggregateStreamCreationException ex) {
            // Handle duplicate aggregate identifier case
            System.err.println("Duplicate aggregate identifier: " + ex.getMessage());
            // Perform any additional logic, like logging or sending a response
        } catch (Exception ex) {
            // Catch any other exceptions
            System.err.println("Unexpected error: " + ex.getMessage());
        }


        return "Scan created with ID: " + scanId;
    }

    @PostMapping("/other")
    public String other(@RequestParam String hardwareId) {
        final UUID scanId = ScanController.BREAK_SCAN_ID;
        Instant scanTime = Instant.now();

        Commands.OtherCommand command = Commands.OtherCommand.builder()
                .scanId(scanId)
                .timestamp(scanTime)
                .build();

        // Send the command on the Axon command bus
        try {
            commandGateway.send(command);
        } catch (AggregateStreamCreationException ex) {
            // Handle duplicate aggregate identifier case
            System.err.println("Duplicate aggregate identifier: " + ex.getMessage());
            // Perform any additional logic, like logging or sending a response
        } catch (Exception ex) {
            // Catch any other exceptions
            System.err.println("Unexpected error: " + ex.getMessage());
        }


        return "Scan created with ID: " + scanId;
    }
}
