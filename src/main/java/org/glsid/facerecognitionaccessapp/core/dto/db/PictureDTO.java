package org.glsid.facerecognitionaccessapp.core.dto.db;

import java.time.LocalDateTime;

public record PictureDTO(
        Long id,
        String name,
        String picturePath,
        String type,
        float[] embeddings,
        Long userId,
        Long roomId,
        Long attemptId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}