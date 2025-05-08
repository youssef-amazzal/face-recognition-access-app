package org.glsid.facerecognitionaccessapp.presentation.components;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.glsid.facerecognitionaccessapp.presentation.utils.JavaFxImageConverter;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.*;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl.FaceCropper;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl.HaarCascadeDetector;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl.FaceNetPreprocessor;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl.FaceNetExtractor;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Styles;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.net.URL;
import java.util.ResourceBundle;

import static org.opencv.imgcodecs.Imgcodecs.imread;

public class Demo extends Component implements Initializable {
    @FXML
    private StackPane root;
    @FXML
    private ImageView FrameView;
    @FXML
    private Button StartCamButton;

    private final IFaceDetector faceDetector = new HaarCascadeDetector();
    private static final IFaceCropper faceCropper = new FaceCropper();
    private final IFacePreprocessor facePreprocessor = new FaceNetPreprocessor();
    private final IFaceFeatureExtractor faceFeatureExtractor = new FaceNetExtractor();
    private boolean isCameraActive = false;
    private VideoCapture camera;
    private Thread webCamThread;
    private volatile boolean running;
    private final float[] mockFeatures;

    public Demo() {
        super(Views.DEMO, Styles.COMMON);
        mockFeatures = getMockEmbedding();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configWindow();

        StartCamButton.setOnAction(_ -> {
            if (!isCameraActive) startCamera();
            else close();
        });
    }

    private void configWindow() {
        FrameView.fitHeightProperty().bind(root.heightProperty());
        FrameView.fitWidthProperty().bind(root.widthProperty());
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
            isCameraActive = true;
            running = true;
            Platform.runLater(() -> StartCamButton.setText("Stop Camera"));
            webCamThread = new Thread(() -> {
                Mat frame = new Mat();
                while (running) {
                    if (camera.read(frame)) {
                        Mat clonedFrame = frame.clone();
                        Rect detectedFace = faceDetector.detectFace(clonedFrame);
                        if (detectedFace != null) {
                            Mat preprocessedFace = facePreprocessor.preprocessFace(faceCropper.cropFace(frame.clone(), detectedFace));
                            float[] features = faceFeatureExtractor.extractFeatures(preprocessedFace);
                            var similarity =  calculateSimilarity(features, mockFeatures);
                            similarity = Math.round(similarity * 100.0);
                            Imgproc.rectangle(clonedFrame, detectedFace.tl(), detectedFace.br(), new Scalar(255, 0, 0), 1);
                            Imgproc.putText(clonedFrame, String.format("Similarity: %d%%", (int) similarity), detectedFace.tl(), 0, 0.5, new Scalar(0, 0, 255));
                        }

                        Image imageToShow = JavaFxImageConverter.toImage(clonedFrame);
                        Platform.runLater(() -> FrameView.setImage(imageToShow));
                    }
                }
            });
            webCamThread.setDaemon(true);
            webCamThread.start();
        }).start();
    }

    public void close() {
        isCameraActive = false;
        running = false;
        StartCamButton.setText("Start Camera");
        try {
            if (webCamThread != null) {
                webCamThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (camera != null) {
            camera.release();
        }
        Platform.runLater(() -> FrameView.setImage(null));
    }

    float[] getMockEmbedding() {
        Mat imgMat2 = imread("src/main/resources/org/glsid/facerecognitionaccessapp/DSC07645.JPG");
        Rect detectedFace_2 = faceDetector.detectFace(imgMat2);
        Mat preprocessedFace_2 = facePreprocessor.preprocessFace(faceCropper.cropFace(imgMat2, detectedFace_2));
        return faceFeatureExtractor.extractFeatures(preprocessedFace_2);
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