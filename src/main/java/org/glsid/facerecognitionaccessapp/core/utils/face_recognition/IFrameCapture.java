package org.glsid.facerecognitionaccessapp.core.utils.face_recognition;

import org.opencv.core.Mat;

public interface IFrameCapture {
    void startCamera();
    void stopCamera();
    Mat captureFrame();
}
