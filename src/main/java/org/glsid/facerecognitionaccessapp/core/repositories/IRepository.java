package org.glsid.facerecognitionaccessapp.core.repositories;

import org.glsid.facerecognitionaccessapp.core.exceptions.Maybe;

import java.util.List;

public interface IRepository<I, E> {
    E save(E entity);
    E update(E entity);
    E delete(I id);
    Maybe<E> findById(I id);
    Maybe<List<E>> findAll();
}
