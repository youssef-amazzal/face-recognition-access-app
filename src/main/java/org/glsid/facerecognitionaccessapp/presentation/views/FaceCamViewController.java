package org.glsid.facerecognitionaccessapp.presentation.views;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.JavaFxImageConverter;
import org.glsid.facerecognitionaccessapp.presentation.router.RoutableView;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl.HaarCascadeDetector;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.IFaceDetector;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.net.URL;
import java.util.ResourceBundle;


public class FaceCamViewController extends RoutableView implements Initializable {

    @FXML private StackPane root;
    @FXML private ImageView FrameView;
    @FXML private final IFaceDetector faceDetector = new HaarCascadeDetector();

    private VideoCapture camera;
    private Thread webCamThread;
    private volatile boolean running;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FrameView.fitHeightProperty().bind(root.heightProperty());
        FrameView.fitWidthProperty().bind(root.widthProperty());
        startCamera();
    }

    private void startCamera() {
        new Thread(() -> {
            camera = new VideoCapture(0);
            if (!camera.isOpened()) {
                Platform.runLater(() -> {
                    throw new RuntimeException("Failed to open camera.");
                });
                return;
            }
            running = true;
            webCamThread = new Thread(() -> {
                Mat frame = new Mat();
                while (running) {
                    if (camera.read(frame)) {
                        Mat clonedFrame = frame.clone();
                        Rect detectedFace = faceDetector.detectFace(clonedFrame);
                        if (detectedFace != null) drawRect(clonedFrame, detectedFace);
                        Image image = JavaFxImageConverter.toImage(clonedFrame);
                        Platform.runLater(() -> FrameView.setImage(image));
                    }
                }
            });
            webCamThread.setDaemon(true);
            webCamThread.start();
        }).start();
    }

    private static void drawRect(Mat frame, Rect detectedFace) {
        Imgproc.rectangle(frame, detectedFace.tl(), detectedFace.br(), new Scalar(255, 0, 0), 1);
    }

    @Override
    public void close() {
        try {
            Platform.runLater(() -> FrameView.setImage(null));
            running = false;
            if (webCamThread != null) webCamThread.join();
            if (camera != null) camera.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}