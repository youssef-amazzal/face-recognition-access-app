package org.glsid.facerecognitionaccessapp.core.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public final class Permission {
    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String allowedDays;
    private Door door;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Permission(
            Long id,
            String title,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            LocalTime startTime,
            LocalTime endTime,
            String allowedDays,
            Door door,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.allowedDays = allowedDays;
        this.door = door;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long id() {
        return id;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    public LocalDate startDate() {
        return startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }

    public LocalTime startTime() {
        return startTime;
    }

    public LocalTime endTime() {
        return endTime;
    }

    public String allowedDays() {
        return allowedDays;
    }

    public Door door() {
        return door;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setAllowedDays(String allowedDays) {
        this.allowedDays = allowedDays;
    }

    public void setDoor(Door door) {
        this.door = door;
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
        var that = (Permission) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.title, that.title) &&
                Objects.equals(this.description, that.description) &&
                Objects.equals(this.startDate, that.startDate) &&
                Objects.equals(this.endDate, that.endDate) &&
                Objects.equals(this.startTime, that.startTime) &&
                Objects.equals(this.endTime, that.endTime) &&
                Objects.equals(this.allowedDays, that.allowedDays) &&
                Objects.equals(this.door, that.door) &&
                Objects.equals(this.createdAt, that.createdAt) &&
                Objects.equals(this.updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, startDate, endDate, startTime, endTime, allowedDays, door, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Permission[" +
                "id=" + id + ", " +
                "title=" + title + ", " +
                "description=" + description + ", " +
                "startDate=" + startDate + ", " +
                "endDate=" + endDate + ", " +
                "startTime=" + startTime + ", " +
                "endTime=" + endTime + ", " +
                "allowedDays=" + allowedDays + ", " +
                "door=" + door + ", " +
                "createdAt=" + createdAt + ", " +
                "updatedAt=" + updatedAt + ']';
    }

}

