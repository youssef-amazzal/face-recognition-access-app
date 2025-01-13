package org.glsid.facerecognitionaccessapp.core.entities;

import java.time.LocalDateTime;

public record UserPermission(
        Long id,
        User user,
        Permission permission,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    enum Status {
        PENDING("pending"),
        ACTIVE("active"),
        INACTIVE("inactive");

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
