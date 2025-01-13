package org.glsid.facerecognitionaccessapp.core.dto;

import java.time.LocalDateTime;

public record PictureDTO(
        Long id,
        String name,
        String picturePath,
        String type,
        Long userId,
        Long roomId,
        Long attemptId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}