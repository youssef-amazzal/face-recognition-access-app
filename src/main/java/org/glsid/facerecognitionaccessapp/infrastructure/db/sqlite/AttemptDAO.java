package org.glsid.facerecognitionaccessapp.infrastructure.db.sqlite;

import org.glsid.facerecognitionaccessapp.core.dto.AttemptDTO;
import org.glsid.facerecognitionaccessapp.core.dao.IAttemptDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttemptDAO implements IAttemptDAO {
    final Connection connection;

    public AttemptDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public AttemptDTO save(AttemptDTO entity) {
        String sql = "INSERT INTO attempts (user_id, door_id, status, confidence, reason, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, entity.userId());
            pstmt.setLong(2, entity.doorId());
            pstmt.setString(3, entity.status());
            pstmt.setFloat(4, entity.confidence());
            pstmt.setString(5, entity.reason());
            pstmt.setTimestamp(6, Timestamp.valueOf(entity.createdAt()));
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return new AttemptDTO(
                                generatedKeys.getLong(1),
                                entity.userId(),
                                entity.doorId(),
                                entity.status(),
                                entity.confidence(),
                                entity.reason(),
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
    public void update(AttemptDTO entity) {
        String sql = "UPDATE attempts SET user_id = ?, door_id = ?, status = ?, confidence = ?, reason = ?, created_at = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, entity.userId());
            pstmt.setLong(2, entity.doorId());
            pstmt.setString(3, entity.status());
            pstmt.setFloat(4, entity.confidence());
            pstmt.setString(5, entity.reason());
            pstmt.setTimestamp(6, Timestamp.valueOf(entity.createdAt()));
            pstmt.setTimestamp(7, Timestamp.valueOf(entity.updatedAt()));
            pstmt.setLong(8, entity.id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM attempts WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<AttemptDTO> findById(Long id) {
        String sql = "SELECT * FROM attempts WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    AttemptDTO attempt = RsToDTO(rs);
                    return Optional.of(attempt);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<AttemptDTO> findAll() {
        String sql = "SELECT * FROM attempts";
        List<AttemptDTO> attempts = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                AttemptDTO attempt = RsToDTO(rs);
                attempts.add(attempt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attempts;
    }

    @Override
    public AttemptDTO RsToDTO(ResultSet rs) throws SQLException {
        return new AttemptDTO(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("door_id"),
                rs.getString("status"),
                rs.getFloat("confidence"),
                rs.getString("reason"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}