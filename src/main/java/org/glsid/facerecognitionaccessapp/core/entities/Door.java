package org.glsid.facerecognitionaccessapp.core.entities;

import java.time.LocalDateTime;

public record Door(
        Long id,
        String name,
        String description,
        Picture picture,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
