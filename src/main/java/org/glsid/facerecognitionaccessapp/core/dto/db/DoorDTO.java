package org.glsid.facerecognitionaccessapp.core.dto.db;

import java.time.LocalDateTime;

public record DoorDTO(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}