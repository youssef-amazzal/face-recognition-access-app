package org.glsid.facerecognitionaccessapp.presentation.Constants;

public enum Icons {
    MDI_FILE_DOCUMENT("mdi2f-file-document"),
    MDI_FACE_RECOGNITION("mdi2f-face-recognition"),
    MDI_LOCK("mdi2l-lock"),
    MDI_CHECK_CIRCLE("mdi2c-check-circle"),
    ;

    private final String value;

    Icons(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
