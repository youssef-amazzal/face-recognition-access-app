package org.glsid.facerecognitionaccessapp.core.entities;

import java.time.LocalDateTime;
import java.util.List;

public record User(
        Long id,
        String firstName,
        String lastName,
        String bio,
        List<Picture> pictures,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
