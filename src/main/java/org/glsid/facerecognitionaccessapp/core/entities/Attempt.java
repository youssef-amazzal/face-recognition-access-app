package org.glsid.facerecognitionaccessapp.core.entities;

import java.time.LocalDateTime;

public record Attempt(
        Long id,
        User user,
        Door door,
        Status status,
        Float confidence,
        String reason,
        Picture picture,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    enum Status {
        SUCCESS("success"),
        FAILURE("failure"),
        UNKNOWN("unknown");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
