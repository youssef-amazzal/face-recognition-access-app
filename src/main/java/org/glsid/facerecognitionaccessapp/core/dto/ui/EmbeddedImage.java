package org.glsid.facerecognitionaccessapp.core.dto.ui;

import javafx.scene.image.Image;

public record EmbeddedImage (
        Image image,
        float[] embedding
) {
}
