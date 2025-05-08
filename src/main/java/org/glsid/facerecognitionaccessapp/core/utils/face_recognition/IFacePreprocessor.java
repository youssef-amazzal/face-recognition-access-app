package org.glsid.facerecognitionaccessapp.core.utils.face_recognition;

import org.opencv.core.Mat;

@FunctionalInterface
public interface IFacePreprocessor {
    Mat preprocessFace(Mat croppedFace);
}

