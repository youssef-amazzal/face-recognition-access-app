package org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl;

import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.IFaceDetector;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.util.Objects;

public class HaarCascadeDetector implements IFaceDetector {
    private final CascadeClassifier cascadeClassifier;

    public HaarCascadeDetector() {
        String classifierFilePath = "/org/glsid/facerecognitionaccessapp/models/haarcascade_frontalface_default.xml";
        final String fullPath = Objects.requireNonNull(getClass().getResource(classifierFilePath)).getPath().substring(1);
        cascadeClassifier = new CascadeClassifier(fullPath);
        if (cascadeClassifier.empty()) {
            throw new IllegalArgumentException("Error loading cascade file: " + fullPath);
        }
    }

    @Override
    public Rect detectFace(Mat frame) {
        Mat grayFrame = new Mat();
        Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(grayFrame, grayFrame);

        MatOfRect faces = new MatOfRect();
        cascadeClassifier.detectMultiScale(
                grayFrame, faces, 1.3, 5, 0
//                ,
//                new Size(80, 80),
//                new Size(400, 400)
        );

        Rect largestFace = null;
        double maxArea = 0;

        for (Rect face : faces.toArray()) {
            double area = face.width * face.height;
            if (area > maxArea) {
                maxArea = area;
                largestFace = face;
            }
        }

        return largestFace;
    }
}