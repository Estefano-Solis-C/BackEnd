package com.test.backend.alerts.domain.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThermalExcursionEvaluatorTest {

    private final ThermalExcursionEvaluator evaluator = new ThermalExcursionEvaluator();

    @Test
    @DisplayName("Returns NORMAL when temperature is comfortably inside the cold-chain range")
    void shouldReturnNormalWhenTemperatureIsInsideRange() {
        // Arrange
        double temp = 4.5;
        double min = 2.0;
        double max = 8.0;

        // Act
        var result = evaluator.evaluate(temp, min, max);

        // Assert
        assertEquals(ThermalExcursionEvaluator.ExcursionSeverity.NORMAL, result);
    }

    @Test
    @DisplayName("Returns CRITICAL when temperature drops severely below freezing")
    void shouldReturnCriticalWhenTemperatureDropsSeverelyBelowFreezing() {
        // Arrange
        double temp = -1.5;
        double min = 2.0;
        double max = 8.0;

        // Act
        var result = evaluator.evaluate(temp, min, max);

        // Assert
        assertEquals(ThermalExcursionEvaluator.ExcursionSeverity.CRITICAL, result);
    }

    @Test
    @DisplayName("Returns CRITICAL when temperature rises severely above the safety threshold")
    void shouldReturnCriticalWhenTemperatureRisesSeverelyAboveThreshold() {
        // Arrange
        double temp = 13.0;
        double min = 2.0;
        double max = 8.0;

        // Act
        var result = evaluator.evaluate(temp, min, max);

        // Assert
        assertEquals(ThermalExcursionEvaluator.ExcursionSeverity.CRITICAL, result);
    }

    @Test
    @DisplayName("Returns NORMAL on exact lower boundary limit")
    void shouldReturnNormalOnExactLowerBoundary() {
        // Arrange
        double temp = 2.0;

        // Act
        var result = evaluator.evaluate(temp, 2.0, 8.0);

        // Assert
        assertEquals(ThermalExcursionEvaluator.ExcursionSeverity.NORMAL, result);
    }

    @Test
    @DisplayName("Returns NORMAL on exact upper boundary limit")
    void shouldReturnNormalOnExactUpperBoundary() {
        // Arrange
        double temp = 8.0;

        // Act
        var result = evaluator.evaluate(temp, 2.0, 8.0);

        // Assert
        assertEquals(ThermalExcursionEvaluator.ExcursionSeverity.NORMAL, result);
    }

    @Test
    @DisplayName("Returns NORMAL safely when temperature input is null")
    void shouldReturnNormalSafelyWhenTemperatureIsNull() {
        // Arrange
        Double temp = null;

        // Act
        var result = evaluator.evaluate(temp, 2.0, 8.0);

        // Assert
        assertEquals(ThermalExcursionEvaluator.ExcursionSeverity.NORMAL, result);
    }

    @Test
    @DisplayName("Returns WARNING on minor thermal excursion below threshold")
    void shouldReturnWarningOnMinorExcursionBelowThreshold() {
        // Arrange
        double temp = 1.5;

        // Act
        var result = evaluator.evaluate(temp, 2.0, 8.0);

        // Assert
        assertEquals(ThermalExcursionEvaluator.ExcursionSeverity.WARNING, result);
    }

    @Test
    @DisplayName("Returns WARNING on minor thermal excursion above threshold")
    void shouldReturnWarningOnMinorExcursionAboveThreshold() {
        // Arrange
        double temp = 9.0;

        // Act
        var result = evaluator.evaluate(temp, 2.0, 8.0);

        // Assert
        assertEquals(ThermalExcursionEvaluator.ExcursionSeverity.WARNING, result);
    }
}
