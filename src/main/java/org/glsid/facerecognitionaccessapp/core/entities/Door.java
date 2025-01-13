package org.glsid.facerecognitionaccessapp.core.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Door {
    private Long id;
    private String name;
    private String description;
    private Picture picture;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Door(
            Long id,
            String name,
            String description,
            Picture picture,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public Picture picture() {
        return picture;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Door) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.description, that.description) &&
                Objects.equals(this.picture, that.picture) &&
                Objects.equals(this.createdAt, that.createdAt) &&
                Objects.equals(this.updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, picture, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Door[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "picture=" + picture + ", " +
                "createdAt=" + createdAt + ", " +
                "updatedAt=" + updatedAt + ']';
    }


}
