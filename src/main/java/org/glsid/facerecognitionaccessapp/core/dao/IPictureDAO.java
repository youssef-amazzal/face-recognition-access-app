package org.glsid.facerecognitionaccessapp.core.dao;

import org.glsid.facerecognitionaccessapp.core.dto.db.PictureDTO;

import java.util.List;


public interface IPictureDAO extends IDAO<Long, PictureDTO> {
    List<PictureDTO> findByUserId(Long userId);
}
