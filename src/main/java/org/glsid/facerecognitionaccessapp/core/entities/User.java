package org.glsid.facerecognitionaccessapp.core.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public final class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String notes;
    private boolean active;
    private List<Picture> pictures;
    private List<Permission> permissions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User() {}

    public User(
            Long id,
            String firstName,
            String lastName,
            String notes,
            boolean active,
            List<Picture> pictures,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.notes = notes;
        this.active = active;
        this.pictures = pictures;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long id() {
        return id;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String notes() {
        return notes;
    }

    public List<Picture> pictures() {
        return pictures;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
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
        var that = (User) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.firstName, that.firstName) &&
                Objects.equals(this.lastName, that.lastName) &&
                Objects.equals(this.notes, that.notes) &&
                Objects.equals(this.pictures, that.pictures) &&
                Objects.equals(this.createdAt, that.createdAt) &&
                Objects.equals(this.updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, notes, pictures, createdAt, updatedAt);
    }
}
