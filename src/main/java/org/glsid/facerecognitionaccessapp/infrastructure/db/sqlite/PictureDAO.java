package org.glsid.facerecognitionaccessapp.infrastructure.db.sqlite;

import org.glsid.facerecognitionaccessapp.core.dao.IPictureDAO;
import org.glsid.facerecognitionaccessapp.core.dto.PictureDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PictureDAO implements IPictureDAO {
    final Connection connection;

    public PictureDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public PictureDTO save(PictureDTO entity) {
        String sql = "INSERT INTO pictures (name, picture_path, type, user_id, room_id, attempt_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, entity.name());
            pstmt.setString(2, entity.picturePath());
            pstmt.setString(3, entity.type());
            pstmt.setLong(4, entity.userId());
            pstmt.setLong(5, entity.roomId());
            pstmt.setLong(6, entity.attemptId());
            pstmt.setTimestamp(7, Timestamp.valueOf(entity.createdAt()));
            pstmt.setTimestamp(8, Timestamp.valueOf(entity.updatedAt()));
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return new PictureDTO(
                                generatedKeys.getLong(1),
                                entity.name(),
                                entity.picturePath(),
                                entity.type(),
                                entity.userId(),
                                entity.roomId(),
                                entity.attemptId(),
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
    public void update(PictureDTO entity) {
        String sql = "UPDATE pictures SET name = ?, picture_path = ?, type = ?, user_id = ?, room_id = ?, attempt_id = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.name());
            pstmt.setString(2, entity.picturePath());
            pstmt.setString(3, entity.type());
            pstmt.setLong(4, entity.userId());
            pstmt.setLong(5, entity.roomId());
            pstmt.setLong(6, entity.attemptId());
            pstmt.setTimestamp(7, Timestamp.valueOf(entity.updatedAt()));
            pstmt.setLong(8, entity.id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM pictures WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<PictureDTO> findById(Long id) {
        String sql = "SELECT * FROM pictures WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    PictureDTO picture = RsToDTO(rs);
                    return Optional.of(picture);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<PictureDTO> findAll() {
        String sql = "SELECT * FROM pictures";
        List<PictureDTO> pictures = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PictureDTO picture = RsToDTO(rs);
                pictures.add(picture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pictures;
    }

    @Override
    public PictureDTO RsToDTO(ResultSet rs) throws SQLException {
        return new PictureDTO(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("picture_path"),
                rs.getString("type"),
                rs.getLong("user_id"),
                rs.getLong("room_id"),
                rs.getLong("attempt_id"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}