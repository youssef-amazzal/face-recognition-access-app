package org.glsid.facerecognitionaccessapp.core.dto;
import java.time.LocalDateTime;

public record PermissionDTO(
        Long id,
        String title,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String startTime,
        String endTime,
        String allowedDays,
        Long doorId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}