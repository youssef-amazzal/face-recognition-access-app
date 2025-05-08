package org.glsid.facerecognitionaccessapp.core.dto.ui;

import java.util.List;

public record UserImageFxDTO (
        String firstName,
        String lastName,
        String notes,
        List<EmbeddedImage> images
) {}
