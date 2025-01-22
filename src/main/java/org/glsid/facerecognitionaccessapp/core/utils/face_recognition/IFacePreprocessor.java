package org.glsid.facerecognitionaccessapp.core.utils.face_recognition;


import org.opencv.core.Mat;
import org.opencv.core.Rect;

@FunctionalInterface
public interface IFacePreprocessor {
    Mat preprocessFace(Mat frame, Rect faceRect);
}

