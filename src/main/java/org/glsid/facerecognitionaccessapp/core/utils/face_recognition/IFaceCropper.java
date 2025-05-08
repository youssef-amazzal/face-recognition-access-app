package org.glsid.facerecognitionaccessapp.core.utils.face_recognition;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public interface IFaceCropper {
    Mat cropFace(Mat frame, Rect boundingBox);
}
