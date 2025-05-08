package org.glsid.facerecognitionaccessapp.presentation.utils;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import org.glsid.facerecognitionaccessapp.core.dto.ui.EmbeddedImage;

import java.util.List;

public class StateStorage {
    public static ObjectProperty<List<EmbeddedImage>> capturedImagesProperty = new SimpleObjectProperty<>();
}
