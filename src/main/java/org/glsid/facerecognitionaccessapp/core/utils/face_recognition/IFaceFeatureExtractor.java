package org.glsid.facerecognitionaccessapp.core.utils.face_recognition;


import org.opencv.core.Mat;

@FunctionalInterface
public interface IFaceFeatureExtractor {
    float[] extractFeatures(Mat face);
}