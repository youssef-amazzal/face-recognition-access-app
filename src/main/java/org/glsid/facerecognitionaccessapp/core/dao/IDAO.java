package org.glsid.facerecognitionaccessapp.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IDAO<I, E> {
    E save(E entity);
    void update(E entity);
    void delete(I id);
    Optional<E> findById(I id);
    List<E> findAll();

    E RsToDTO(ResultSet rs) throws SQLException;
}
