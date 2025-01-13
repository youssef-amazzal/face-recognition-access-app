package org.glsid.facerecognitionaccessapp.core.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public final class UserPermission {
    private Long id;
    private User user;
    private Permission permission;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserPermission(
            Long id,
            User user,
            Permission permission,
            Status status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.user = user;
        this.permission = permission;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long id() {
        return id;
    }

    public User user() {
        return user;
    }

    public Permission permission() {
        return permission;
    }

    public Status status() {
        return status;
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

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        var that = (UserPermission) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.user, that.user) &&
                Objects.equals(this.permission, that.permission) &&
                Objects.equals(this.status, that.status) &&
                Objects.equals(this.createdAt, that.createdAt) &&
                Objects.equals(this.updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, permission, status, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "UserPermission[" +
                "id=" + id + ", " +
                "user=" + user + ", " +
                "permission=" + permission + ", " +
                "status=" + status + ", " +
                "createdAt=" + createdAt + ", " +
                "updatedAt=" + updatedAt + ']';
    }


    public enum Status {
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
