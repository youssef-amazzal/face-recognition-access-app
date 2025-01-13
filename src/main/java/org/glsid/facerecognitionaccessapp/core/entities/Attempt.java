package org.glsid.facerecognitionaccessapp.core.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Attempt {
    private Long id;
    private User user;
    private Door door;
    private Status status;
    private Float confidence;
    private String reason;
    private Picture picture;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Attempt(
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
        this.id = id;
        this.user = user;
        this.door = door;
        this.status = status;
        this.confidence = confidence;
        this.reason = reason;
        this.picture = picture;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long id() {
        return id;
    }

    public User user() {
        return user;
    }

    public Door door() {
        return door;
    }

    public Status status() {
        return status;
    }

    public Float confidence() {
        return confidence;
    }

    public String reason() {
        return reason;
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

    public void setUser(User user) {
        this.user = user;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
        var that = (Attempt) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.user, that.user) &&
                Objects.equals(this.door, that.door) &&
                Objects.equals(this.status, that.status) &&
                Objects.equals(this.confidence, that.confidence) &&
                Objects.equals(this.reason, that.reason) &&
                Objects.equals(this.picture, that.picture) &&
                Objects.equals(this.createdAt, that.createdAt) &&
                Objects.equals(this.updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, door, status, confidence, reason, picture, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Attempt[" +
                "id=" + id + ", " +
                "user=" + user + ", " +
                "door=" + door + ", " +
                "status=" + status + ", " +
                "confidence=" + confidence + ", " +
                "reason=" + reason + ", " +
                "picture=" + picture + ", " +
                "createdAt=" + createdAt + ", " +
                "updatedAt=" + updatedAt + ']';
    }

    public enum Status {
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
