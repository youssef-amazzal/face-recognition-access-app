package org.glsid.facerecognitionaccessapp.application.usecases;

import org.glsid.facerecognitionaccessapp.core.exceptions.Maybe;

@FunctionalInterface
public interface IUseCase<I, O> {
    Maybe<O> execute(I input);
}
