package com.test.backend.alerts.application.internal.commandservices;

import com.test.backend.alerts.domain.model.aggregates.Alert;
import com.test.backend.alerts.domain.model.commands.CreateAlertCommand;
import com.test.backend.alerts.domain.model.commands.DeleteAlertCommand;
import com.test.backend.alerts.infrastructure.persistence.jpa.repositories.AlertRepository;
import com.test.backend.history.domain.model.aggregates.HistoryRecord;
import com.test.backend.history.infrastructure.persistence.jpa.repositories.HistoryRecordRepository;
import com.test.backend.labs.domain.model.aggregates.Laboratory;
import com.test.backend.labs.infrastructure.persistence.jpa.repositories.LaboratoryRepository;
import com.test.backend.shared.application.CurrentWorkspaceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlertCommandServiceImplTest {

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private LaboratoryRepository laboratoryRepository;

    @Mock
    private CurrentWorkspaceService currentWorkspaceService;

    @Mock
    private HistoryRecordRepository historyRecordRepository;

    @InjectMocks
    private AlertCommandServiceImpl service;

    @Test
    @DisplayName("handle(CreateAlertCommand) should persist alert and history record when valid (AAA)")
    void handle_CreateAlert_ShouldPersistAndReturnAlert_WhenValid() {
        // Arrange
        var command = new CreateAlertCommand(
                "Temperature Breach",
                "ULT Freezer temperature exceeded -75°C",
                "Critical",
                "Active",
                "Bio-Safety Lab 04",
                "Just now",
                1L
        );

        when(currentWorkspaceService.getCurrentWorkspaceId()).thenReturn(Optional.of(1L));
        var lab = new Laboratory();
        when(laboratoryRepository.findByIdAndWorkspaceId(1L, 1L)).thenReturn(Optional.of(lab));
        when(alertRepository.save(any(Alert.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Optional<Alert> result = service.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Temperature Breach", result.get().getTitle());
        verify(alertRepository).save(any(Alert.class));
        verify(historyRecordRepository).save(any(HistoryRecord.class));
    }

    @Test
    @DisplayName("handle(DeleteAlertCommand) should delete alert when it exists in current workspace (AAA)")
    void handle_DeleteAlert_ShouldInvokeRepositoryDelete_WhenExists() {
        // Arrange
        var command = new DeleteAlertCommand(5L);
        when(currentWorkspaceService.getCurrentWorkspaceId()).thenReturn(Optional.of(1L));
        var alert = new Alert();
        when(alertRepository.findByIdAndLaboratoryWorkspaceId(5L, 1L)).thenReturn(Optional.of(alert));

        // Act
        service.handle(command);

        // Assert
        verify(alertRepository).findByIdAndLaboratoryWorkspaceId(5L, 1L);
        verify(alertRepository).delete(alert);
    }
}
