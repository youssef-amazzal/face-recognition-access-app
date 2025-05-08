package org.glsid.facerecognitionaccessapp.core.dto.db;

import java.time.LocalDateTime;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String notes,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}