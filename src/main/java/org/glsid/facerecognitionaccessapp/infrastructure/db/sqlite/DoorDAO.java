package org.glsid.facerecognitionaccessapp.infrastructure.db.sqlite;

import org.glsid.facerecognitionaccessapp.core.dao.IDoorDAO;
import org.glsid.facerecognitionaccessapp.core.dto.DoorDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoorDAO implements IDoorDAO {
    final Connection connection;

    public DoorDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public DoorDTO save(DoorDTO entity) {
        String sql = "INSERT INTO doors (name, description, created_at, updated_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, entity.name());
            pstmt.setString(2, entity.description());
            pstmt.setTimestamp(3, Timestamp.valueOf(entity.createdAt()));
            pstmt.setTimestamp(4, Timestamp.valueOf(entity.updatedAt()));
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return new DoorDTO(
                                generatedKeys.getLong(1),
                                entity.name(),
                                entity.description(),
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
    public void update(DoorDTO entity) {
        String sql = "UPDATE doors SET name = ?, description = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.name());
            pstmt.setString(2, entity.description());
            pstmt.setTimestamp(3, Timestamp.valueOf(entity.updatedAt()));
            pstmt.setLong(4, entity.id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM doors WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<DoorDTO> findById(Long id) {
        String sql = "SELECT * FROM doors WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    DoorDTO door = RsToDTO(rs);
                    return Optional.of(door);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<DoorDTO> findAll() {
        String sql = "SELECT * FROM doors";
        List<DoorDTO> doors = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                DoorDTO door = RsToDTO(rs);
                doors.add(door);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doors;
    }

    @Override
    public DoorDTO RsToDTO(ResultSet rs) throws SQLException {
        return new DoorDTO(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}