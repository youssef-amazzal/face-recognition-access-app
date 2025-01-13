package org.glsid.facerecognitionaccessapp.infrastructure.db.sqlite;

import org.glsid.facerecognitionaccessapp.core.dao.IPermissionDAO;
import org.glsid.facerecognitionaccessapp.core.dto.PermissionDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PermissionDAO implements IPermissionDAO {
    final Connection connection;

    public PermissionDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public PermissionDTO save(PermissionDTO entity) {
        String sql = "INSERT INTO permissions (title, description, start_date, end_date, start_time, end_time, allowed_days, door_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, entity.title());
            pstmt.setString(2, entity.description());
            pstmt.setTimestamp(3, Timestamp.valueOf(entity.startDate()));
            pstmt.setTimestamp(4, Timestamp.valueOf(entity.endDate()));
            pstmt.setString(5, entity.startTime());
            pstmt.setString(6, entity.endTime());
            pstmt.setString(7, entity.allowedDays());
            pstmt.setLong(8, entity.doorId());
            pstmt.setTimestamp(9, Timestamp.valueOf(entity.createdAt()));
            pstmt.setTimestamp(10, Timestamp.valueOf(entity.updatedAt()));
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return new PermissionDTO(
                                generatedKeys.getLong(1),
                                entity.title(),
                                entity.description(),
                                entity.startDate(),
                                entity.endDate(),
                                entity.startTime(),
                                entity.endTime(),
                                entity.allowedDays(),
                                entity.doorId(),
                                entity.createdAt(),
                                entity.updatedAt()
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public void update(PermissionDTO entity) {
        String sql = "UPDATE permissions SET title = ?, description = ?, start_date = ?, end_date = ?, start_time = ?, end_time = ?, allowed_days = ?, door_id = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.title());
            pstmt.setString(2, entity.description());
            pstmt.setTimestamp(3, Timestamp.valueOf(entity.startDate()));
            pstmt.setTimestamp(4, Timestamp.valueOf(entity.endDate()));
            pstmt.setString(5, entity.startTime());
            pstmt.setString(6, entity.endTime());
            pstmt.setString(7, entity.allowedDays());
            pstmt.setLong(8, entity.doorId());
            pstmt.setTimestamp(9, Timestamp.valueOf(entity.updatedAt()));
            pstmt.setLong(10, entity.id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM permissions WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<PermissionDTO> findById(Long id) {
        String sql = "SELECT * FROM permissions WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    PermissionDTO permission = RsToDTO(rs);
                    return Optional.of(permission);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<PermissionDTO> findAll() {
        String sql = "SELECT * FROM permissions";
        List<PermissionDTO> permissions = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PermissionDTO permission = RsToDTO(rs);
                permissions.add(permission);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return permissions;
    }

    @Override
    public PermissionDTO RsToDTO(ResultSet rs) throws SQLException {
        return new PermissionDTO(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getTimestamp("start_date").toLocalDateTime(),
                rs.getTimestamp("end_date").toLocalDateTime(),
                rs.getString("start_time"),
                rs.getString("end_time"),
                rs.getString("allowed_days"),
                rs.getLong("door_id"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}