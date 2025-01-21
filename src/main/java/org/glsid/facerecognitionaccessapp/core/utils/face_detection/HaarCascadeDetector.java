package org.glsid.facerecognitionaccessapp.core.utils.face_detection;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.bytedeco.opencv.global.opencv_imgproc.*;

public class HaarCascadeDetector implements IFaceDetector {
    private final CascadeClassifier faceCascade;

    public HaarCascadeDetector() {
        String classifierFilePath = "/org/glsid/facerecognitionaccessapp/models/haarcascade_frontalface_default.xml";
        final String fullPath = Objects.requireNonNull(getClass().getResource(classifierFilePath)).getPath().substring(1);
        faceCascade = new CascadeClassifier(fullPath);
        if (faceCascade.empty()) {
            throw new IllegalArgumentException("Error loading cascade file: " + fullPath);
        }
    }

    @Override
    public List<Rect> apply(Frame frame) {
        try (OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat()) {
            Mat mat = converter.convertToMat(frame);
            Mat grayMat = new Mat();
            cvtColor(mat, grayMat, COLOR_BGR2GRAY);
            resize(grayMat, grayMat, new Size(grayMat.cols() / 2, grayMat.rows() / 2));

            RectVector faces = new RectVector();
            faceCascade.detectMultiScale(grayMat, faces, 1.1, 3, 0, new Size(30, 30), new Size());

            List<Rect> faceList = new ArrayList<>();
            int frameHeight = mat.rows();
            for (int i = 0; i < faces.size(); i++) {
                Rect face = faces.get(i);
                face.x(face.x() * 2);
                face.y(face.y() * 2);
                face.width(face.width() * 2);
                face.height(face.height() * 2);
                if (face.height() >= frameHeight * 0.2) {
                    faceList.add(face);
                }
            }
            return faceList;
        }
    }
}