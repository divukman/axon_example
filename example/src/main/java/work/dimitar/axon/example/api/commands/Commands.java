package work.dimitar.axon.example.api.commands;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;
import java.util.UUID;

/**
 * Example commands.
 */
public interface Commands {

    @RequiredArgsConstructor
    abstract class ScanCommand implements Commands {
        @TargetAggregateIdentifier
        @NonNull
        public final UUID scanId;
    }

    final class ReceiveScanCommand extends ScanCommand {
        @NonNull
        public final String hardwareId;
        @NonNull
        public final Instant scanTime;


        @Builder
        public ReceiveScanCommand(
                @NonNull final UUID scanId,
                @NonNull final String hardwareId,
                @NonNull final Instant scanTime) {
            super(scanId);
            this.hardwareId = hardwareId;
            this.scanTime = scanTime;
        }
    }

    final class OtherCommand extends ScanCommand {
        @NonNull
        public final Instant timestamp;

        @Builder
        public OtherCommand(
                @NonNull final UUID scanId,
                @NonNull final Instant timestamp) {
            super(scanId);
            this.timestamp = timestamp;
        }
    }

}
