package org.glsid.facerecognitionaccessapp.core.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Picture {
    private Long id;
    private String name;
    private String image_path;
    private Types type;
    private float[] embeddings;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Picture(
            Long id,
            String name,
            String image_path,
            Types type,
            float[] embeddings,
            LocalDateTime created_at,
            LocalDateTime updated_at
    ) {
        this.id = id;
        this.name = name;
        this.image_path = image_path;
        this.type = type;
        this.embeddings = embeddings;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String image_path() {
        return image_path;
    }

    public Types type() {
        return type;
    }

    public LocalDateTime created_at() {
        return created_at;
    }

    public LocalDateTime updated_at() {
        return updated_at;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public float[] getEmbeddings() {
        return embeddings;
    }

    public void setEmbeddings(float[] embeddings) {
        this.embeddings = embeddings;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public enum Types {
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
