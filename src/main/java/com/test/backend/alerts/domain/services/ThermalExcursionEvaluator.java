package com.test.backend.alerts.domain.services;

import org.springframework.stereotype.Service;

@Service
public class ThermalExcursionEvaluator {

    public static final double DEFAULT_MIN_TEMP = 2.0;
    public static final double DEFAULT_MAX_TEMP = 8.0;

    public enum ExcursionSeverity {
        NORMAL,
        WARNING,
        CRITICAL
    }

    public ExcursionSeverity evaluate(Double temperature, Double minThreshold, Double maxThreshold) {
        if (temperature == null) {
            return ExcursionSeverity.NORMAL;
        }

        double min = (minThreshold != null) ? minThreshold : DEFAULT_MIN_TEMP;
        double max = (maxThreshold != null) ? maxThreshold : DEFAULT_MAX_TEMP;

        if (temperature < min - 2.0 || temperature > max + 3.0) {
            return ExcursionSeverity.CRITICAL;
        }
        if (temperature < min || temperature > max) {
            return ExcursionSeverity.WARNING;
        }
        return ExcursionSeverity.NORMAL;
    }
}
