package org.glsid.facerecognitionaccessapp.application.usecases;

import java.util.Optional;

@FunctionalInterface
public interface IUseCase<I, O> {
    O execute(I input);
}
