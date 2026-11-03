package com.test.backend.alerts.infrastructure.messaging.listeners;

import com.test.backend.alerts.domain.model.events.ThresholdsChangedEvent;
import com.test.backend.alerts.domain.services.ThresholdProjectionPort;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ThresholdsChangedConsumerListener {

    private final ThresholdProjectionPort thresholdProjectionPort;

    public ThresholdsChangedConsumerListener(ThresholdProjectionPort thresholdProjectionPort) {
        this.thresholdProjectionPort = thresholdProjectionPort;
    }

    @EventListener
    public void on(ThresholdsChangedEvent event) {
        thresholdProjectionPort.updateThresholds(
                event.laboratoryId(),
                event.minThreshold(),
                event.maxThreshold()
        );
    }
}
