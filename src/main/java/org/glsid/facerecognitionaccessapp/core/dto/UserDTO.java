package org.glsid.facerecognitionaccessapp.core.dto;

import java.time.LocalDateTime;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String bio,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}