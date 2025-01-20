package org.glsid.facerecognitionaccessapp.core.exceptions;

sealed public abstract class Try<T> permits Success, Failure {

    public static <T> Try<T> apply(CheckedSupplier<T> function) {
        try {
            var result = function.get();
            return new Success<>(result);
        } catch (Exception ex) {
            return new Failure<>(ex);
        }
    }
}
