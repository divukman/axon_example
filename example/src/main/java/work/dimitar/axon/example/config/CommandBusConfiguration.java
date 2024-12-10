package work.dimitar.axon.example.config;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.commandhandling.gateway.RetryScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CommandBusConfiguration {

    @Bean
    public CommandGateway commandGateway() {
        // Create a CommandGateway with custom retry scheduler
        return DefaultCommandGateway.builder()
                .retryScheduler(retryScheduler())  // Specify retry scheduler
                .build();
    }

    RetryScheduler retryScheduler() {
        return new RetryScheduler() {
            @Override
            public boolean scheduleRetry(CommandMessage commandMessage, RuntimeException lastFailure, List<Class<? extends Throwable>[]> failures, Runnable commandDispatch) {
                return false;
            }
        };
    }
}