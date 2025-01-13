package org.glsid.facerecognitionaccessapp.core.exceptions;


import java.util.function.Supplier;

sealed public abstract class Maybe<T> permits Success, Failure {

    public static <T> Maybe<T> apply(Supplier<T> function) {
        try {
            var result = function.get();
            return new Success<>(result);
        } catch (Exception ex) {
            return new Failure<>(ex);
        }
    }
}
