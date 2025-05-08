package org.glsid.facerecognitionaccessapp.core.dto.db;

import java.time.LocalDateTime;

public record AttemptDTO(
        Long id,
        Long userId,
        Long doorId,
        String status,
        Float confidence,
        String reason,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
