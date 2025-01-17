package org.glsid.facerecognitionaccessapp.presentation.exceptions;

public class ViewNotFound extends RuntimeException {
    public ViewNotFound(String ViewPath) {
        super("View not found: " + ViewPath);
    }
}
