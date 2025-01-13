package org.glsid.facerecognitionaccessapp.core.entities;

import java.time.LocalDateTime;

public record Picture(
        Long id,
        String name,
        String image_path,
        Types type,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
    enum Types {
        PROFILE("profile"),
        ATTEMPT("attempt"),
        ROOM("room");

        private final String value;

        Types(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
