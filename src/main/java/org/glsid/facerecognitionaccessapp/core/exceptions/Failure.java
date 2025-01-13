package org.glsid.facerecognitionaccessapp.core.exceptions;

public final class Failure<T> extends Maybe<T> {
    private final Exception exception;

    public Failure(Exception exception) {
        this.exception = exception;
    }

    public Exception value() {
        return exception;
    }
}
