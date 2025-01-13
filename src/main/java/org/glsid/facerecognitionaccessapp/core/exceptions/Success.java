package org.glsid.facerecognitionaccessapp.core.exceptions;

import java.util.Optional;

public final class Success<T> extends Maybe<T> {
    private final T t;

    public Success(T t) {
        this.t = t;
    }

    public Optional<T> value() {
        return Optional.ofNullable(t);
    }
}
