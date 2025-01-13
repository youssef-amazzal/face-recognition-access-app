package org.glsid.facerecognitionaccessapp.application.usecases;

import java.util.Optional;

@FunctionalInterface
public interface IUseCase<I, O> {
    Optional<O> execute(I input);
}
