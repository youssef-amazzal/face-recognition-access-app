package org.glsid.facerecognitionaccessapp.infrastructure.db.sqlite;

import org.glsid.facerecognitionaccessapp.core.dao.IUserPermissionDAO;
import org.glsid.facerecognitionaccessapp.core.dto.UserPermissionDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserPermissionDAO implements IUserPermissionDAO {
    final Connection connection;

    public UserPermissionDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UserPermissionDTO save(UserPermissionDTO entity) {
        String sql = "INSERT INTO user_permissions (user_id, permission_id, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, entity.userId());
            pstmt.setLong(2, entity.permissionId());
            pstmt.setString(3, entity.status());
            pstmt.setTimestamp(4, Timestamp.valueOf(entity.createdAt()));
            pstmt.setTimestamp(5, Timestamp.valueOf(entity.updatedAt()));
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return new UserPermissionDTO(
                                generatedKeys.getLong(1),
                                entity.userId(),
                                entity.permissionId(),
                                entity.status(),
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
    public void update(UserPermissionDTO entity) {
        String sql = "UPDATE user_permissions SET user_id = ?, permission_id = ?, status = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, entity.userId());
            pstmt.setLong(2, entity.permissionId());
            pstmt.setString(3, entity.status());
            pstmt.setTimestamp(4, Timestamp.valueOf(entity.updatedAt()));
            pstmt.setLong(5, entity.id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM user_permissions WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<UserPermissionDTO> findById(Long id) {
        String sql = "SELECT * FROM user_permissions WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    UserPermissionDTO userPermission = RsToDTO(rs);
                    return Optional.of(userPermission);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<UserPermissionDTO> findAll() {
        String sql = "SELECT * FROM user_permissions";
        List<UserPermissionDTO> userPermissions = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                UserPermissionDTO userPermission = RsToDTO(rs);
                userPermissions.add(userPermission);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userPermissions;
    }

    @Override
    public UserPermissionDTO RsToDTO(ResultSet rs) throws SQLException {
        return new UserPermissionDTO(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("permission_id"),
                rs.getString("status"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}