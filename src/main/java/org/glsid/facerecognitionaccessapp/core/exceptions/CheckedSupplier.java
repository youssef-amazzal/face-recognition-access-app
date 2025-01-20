package org.glsid.facerecognitionaccessapp.core.exceptions;

@FunctionalInterface
public interface CheckedSupplier<T> {
    T get() throws Exception;
}
