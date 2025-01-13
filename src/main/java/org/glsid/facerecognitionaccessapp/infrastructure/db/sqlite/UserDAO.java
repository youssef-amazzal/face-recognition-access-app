package org.glsid.facerecognitionaccessapp.infrastructure.db.sqlite;

import org.glsid.facerecognitionaccessapp.core.dao.IUserDAO;
import org.glsid.facerecognitionaccessapp.core.dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO implements IUserDAO {
    final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UserDTO save(UserDTO entity) {
        String sql = "INSERT INTO users (first_name, last_name, bio, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, entity.firstName());
            pstmt.setString(2, entity.lastName());
            pstmt.setString(3, entity.bio());
            pstmt.setTimestamp(4, Timestamp.valueOf(entity.createdAt()));
            pstmt.setTimestamp(5, Timestamp.valueOf(entity.updatedAt()));
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return new UserDTO(
                                generatedKeys.getLong(1),
                                entity.firstName(),
                                entity.lastName(),
                                entity.bio(),
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
    public void update(UserDTO entity) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, bio = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.firstName());
            pstmt.setString(2, entity.lastName());
            pstmt.setString(3, entity.bio());
            pstmt.setTimestamp(4, Timestamp.valueOf(entity.updatedAt()));
            pstmt.setLong(5, entity.id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    UserDTO user = RsToDTO(rs);
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<UserDTO> findAll() {
        String sql = "SELECT * FROM users";
        List<UserDTO> users = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                UserDTO user = RsToDTO(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public UserDTO RsToDTO(ResultSet rs) throws SQLException {
        return new UserDTO(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("bio"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}