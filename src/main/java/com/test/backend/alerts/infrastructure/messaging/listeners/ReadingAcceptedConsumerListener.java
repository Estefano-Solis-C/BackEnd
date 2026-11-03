package com.test.backend.alerts.infrastructure.messaging.listeners;

import com.test.backend.alerts.domain.model.events.ReadingAcceptedEvent;
import com.test.backend.alerts.domain.services.EvaluateReadingUseCase;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ReadingAcceptedConsumerListener {

    private final EvaluateReadingUseCase evaluateReadingUseCase;

    public ReadingAcceptedConsumerListener(EvaluateReadingUseCase evaluateReadingUseCase) {
        this.evaluateReadingUseCase = evaluateReadingUseCase;
    }

    @EventListener
    public void on(ReadingAcceptedEvent event) {
        evaluateReadingUseCase.evaluate(
                event.laboratoryId(),
                event.temperature(),
                event.minThreshold(),
                event.maxThreshold(),
                event.labName()
        );
    }
}
