package org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl;

import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.IFaceCropper;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class FaceCropper implements IFaceCropper {
    @Override
    public Mat cropFace(Mat frame, Rect boundingBox) {
        if (boundingBox == null) return null;
        return new Mat(frame, boundingBox);
    }
}
