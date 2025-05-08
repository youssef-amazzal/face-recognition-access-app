package org.glsid.facerecognitionaccessapp.core.dto.db;

import java.time.LocalDateTime;

public record UserPermissionDTO(
        Long id,
        Long userId,
        Long permissionId,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}