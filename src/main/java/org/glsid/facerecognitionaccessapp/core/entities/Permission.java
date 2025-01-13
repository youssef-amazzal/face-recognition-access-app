package org.glsid.facerecognitionaccessapp.core.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record Permission(
        Long id,
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        LocalTime startTime,
        LocalTime endTime,
        String allowedDays,
        Door door,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

