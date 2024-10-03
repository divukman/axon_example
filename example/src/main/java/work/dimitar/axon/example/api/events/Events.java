package work.dimitar.axon.example.api.events;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

/**
 * Example events.
 */
public interface Events {

    @ToString
    @RequiredArgsConstructor
    abstract class ScanEvent implements Events {
        @NonNull
        public final UUID scanId;
    }

    @ToString(callSuper = true)
    final class ScanReceivedEvent extends ScanEvent {
        @NonNull
        public final String hardwareId;
        @NonNull
        public final Instant scanTime;

        @Builder
        public ScanReceivedEvent(
                @NonNull final UUID scanId,
                @NonNull final String hardwareId,
                @NonNull final Instant scanTime) {
            super(scanId);
            this.hardwareId = hardwareId;
            this.scanTime = scanTime;
        }
    }

    @ToString(callSuper = true)
    final class OtherEvent extends ScanEvent {
        @NonNull
        public final Instant timestamp;

        @Builder
        public OtherEvent(
                @NonNull final UUID scanId,
                @NonNull final Instant timestamp) {
            super(scanId);
            this.timestamp = timestamp;
        }
    }

}
