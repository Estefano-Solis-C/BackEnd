package com.test.backend.labs.application.internal.commandservices;

import com.test.backend.labs.domain.model.aggregates.Laboratory;
import com.test.backend.labs.domain.model.aggregates.Workspace;
import com.test.backend.labs.domain.model.commands.CreateLaboratoryCommand;
import com.test.backend.labs.domain.model.commands.DeleteLaboratoryCommand;
import com.test.backend.labs.infrastructure.persistence.jpa.repositories.LaboratoryRepository;
import com.test.backend.labs.infrastructure.persistence.jpa.repositories.WorkspaceRepository;
import com.test.backend.shared.application.CurrentWorkspaceService;
import com.test.backend.telemetry.infrastructure.persistence.jpa.repositories.MetricTypeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LaboratoryCommandServiceImplTest {

    @Mock
    private LaboratoryRepository laboratoryRepository;

    @Mock
    private MetricTypeRepository metricTypeRepository;

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Mock
    private CurrentWorkspaceService currentWorkspaceService;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private LaboratoryCommandServiceImpl service;

    @Test
    @DisplayName("handle(CreateLaboratoryCommand) should create and save laboratory when data is valid (AAA)")
    void handle_CreateLaboratory_ShouldPersistAndReturnLaboratory_WhenValid() {
        // Arrange
        var command = new CreateLaboratoryCommand(
                "Bio-Safety Lab",
                "Biohazard Level 3",
                "Operational",
                "Building A",
                "Floor 2",
                "LAB-BSL-01",
                "204",
                "High containment unit",
                "Operational",
                true,
                true,
                null,
                null,
                Collections.emptyList(),
                null
        );

        when(laboratoryRepository.findByLabCode("LAB-BSL-01")).thenReturn(Optional.empty());
        when(currentWorkspaceService.getCurrentWorkspaceId()).thenReturn(Optional.of(1L));
        var workspace = new Workspace();
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspace));
        when(laboratoryRepository.save(any(Laboratory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Optional<Laboratory> result = service.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Bio-Safety Lab", result.get().getName());
        assertEquals("LAB-BSL-01", result.get().getLabCode());
        verify(laboratoryRepository).findByLabCode("LAB-BSL-01");
        verify(laboratoryRepository).save(any(Laboratory.class));
    }

    @Test
    @DisplayName("handle(CreateLaboratoryCommand) should throw exception when labCode already exists (AAA)")
    void handle_CreateLaboratory_ShouldThrowException_WhenLabCodeExists() {
        // Arrange
        var command = new CreateLaboratoryCommand(
                "Bio-Safety Lab",
                "Biohazard Level 3",
                "Operational",
                "Building A",
                "Floor 2",
                "LAB-BSL-01",
                "204",
                "High containment unit",
                "Operational",
                true,
                true,
                null,
                null,
                Collections.emptyList(),
                null
        );

        when(laboratoryRepository.findByLabCode("LAB-BSL-01")).thenReturn(Optional.of(new Laboratory()));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        verify(laboratoryRepository).findByLabCode("LAB-BSL-01");
        verify(laboratoryRepository, never()).save(any());
    }

    @Test
    @DisplayName("handle(DeleteLaboratoryCommand) should delete laboratory and related data when found (AAA)")
    void handle_DeleteLaboratory_ShouldInvokeRepositoryDelete_WhenExists() {
        // Arrange
        var command = new DeleteLaboratoryCommand(10L);
        when(currentWorkspaceService.getCurrentWorkspaceId()).thenReturn(Optional.of(1L));
        var laboratory = new Laboratory();
        when(laboratoryRepository.findByIdAndWorkspaceId(10L, 1L)).thenReturn(Optional.of(laboratory));
        var queryMock = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(queryMock);
        when(queryMock.setParameter(anyInt(), any())).thenReturn(queryMock);

        // Act
        service.handle(command);

        // Assert
        verify(laboratoryRepository).findByIdAndWorkspaceId(10L, 1L);
        verify(laboratoryRepository).delete(laboratory);
    }
}
