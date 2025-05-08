package org.glsid.facerecognitionaccessapp.presentation;

import nu.pattern.OpenCV;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.*;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl.*;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import static org.opencv.imgcodecs.Imgcodecs.imread;

public class Demo {
    static {
        OpenCV.loadLocally();
    }

    private static final IFaceDetector faceDetector = new HaarCascadeDetector();
    private static final IFaceCropper faceCropper = new FaceCropper();
    private static final IFacePreprocessor facePreprocessor = new FaceNetPreprocessor();
    private static final IFaceFeatureExtractor faceFeatureExtractor = new FaceNetExtractor();



    public static void main(String[] args) {
        Mat imgMat1 = imread("src/main/resources/org/glsid/facerecognitionaccessapp/cv.png");
        Mat imgMat2 = imread("src/main/resources/org/glsid/facerecognitionaccessapp/cv.png");

        Rect detectedFace_1 = faceDetector.detectFace(imgMat1);
        Mat preprocessedFace_1 = facePreprocessor.preprocessFace(faceCropper.cropFace(imgMat1, detectedFace_1));
        float[] features_1 = faceFeatureExtractor.extractFeatures(preprocessedFace_1);

        Rect detectedFace_2 = faceDetector.detectFace(imgMat2);
        Mat preprocessedFace_2 = facePreprocessor.preprocessFace(faceCropper.cropFace(imgMat2, detectedFace_2));
        float[] features_2 = faceFeatureExtractor.extractFeatures(preprocessedFace_2);

        System.out.println("Similarity: " + new Demo().calculateSimilarity(features_1, features_2));
    }

    public double calculateSimilarity(float[] embedding1, float[] embedding2) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (int i = 0; i < embedding1.length; i++) {
            dotProduct += embedding1[i] * embedding2[i];
            norm1 += embedding1[i] * embedding1[i];
            norm2 += embedding2[i] * embedding2[i];
        }

        // Calculate cosine similarity
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}
