package org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl;

import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.IFacePreprocessor;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class FaceNetPreprocessor implements IFacePreprocessor {
    private static final int FACENET_INPUT_SIZE = 160;

    @Override
    public Mat preprocessFace(Mat frame, Rect boundingBox) {
        if (boundingBox == null) throw new IllegalArgumentException("boundingBox cannot be null");

        Mat croppedFace = new Mat(frame, boundingBox);
        Mat processedFace = new Mat();
        Imgproc.resize(croppedFace, processedFace, new Size(FACENET_INPUT_SIZE, FACENET_INPUT_SIZE));
        processedFace.convertTo(processedFace, CvType.CV_32F, 1.0 / 255);
        return processedFace;
    }
}