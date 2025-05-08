package org.glsid.facerecognitionaccessapp.presentation.views;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.glsid.facerecognitionaccessapp.core.dto.ui.EmbeddedImage;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.IFaceFeatureExtractor;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.IFacePreprocessor;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl.FaceNetExtractor;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl.FaceNetPreprocessor;
import org.glsid.facerecognitionaccessapp.presentation.utils.StateStorage;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.IFaceCropper;
import org.glsid.facerecognitionaccessapp.presentation.utils.JavaFxImageConverter;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl.FaceCropper;
import org.glsid.facerecognitionaccessapp.presentation.router.RoutableView;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl.HaarCascadeDetector;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.IFaceDetector;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FaceCamViewController extends RoutableView implements Initializable {

    @FXML private StackPane root;
    @FXML private ImageView FrameView;
    @FXML private Button CaptureButton;
    @FXML private VBox CapturedImages;
    @FXML private List<EmbeddedImage> capturedImagesList = new ArrayList<>();

    private VideoCapture camera;
    private Thread webCamThread;
    private volatile boolean running;

    private final IFaceDetector faceDetector = new HaarCascadeDetector();
    private static final IFaceCropper faceCropper = new FaceCropper();
    private final IFacePreprocessor facePreprocessor = new FaceNetPreprocessor();
    private final IFaceFeatureExtractor faceFeatureExtractor = new FaceNetExtractor();

    private final ObjectProperty<Mat> detectedFaceImage = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FrameView.fitHeightProperty().bind(root.heightProperty());
        FrameView.fitWidthProperty().bind(root.widthProperty());
        CaptureButton.setOnAction(_ -> onCapture());
        detectedFaceImage.addListener((_, _, newImage) -> CaptureButton.setDisable(newImage == null));
        startCamera();
    }

    private void onCapture() {
        Mat face = detectedFaceImage.get();

        Image faceImage = JavaFxImageConverter.toImage(face);
        float[] embeddings = faceFeatureExtractor.extractFeatures(facePreprocessor.preprocessFace(face));
        var embeddedImage = new EmbeddedImage(faceImage, embeddings);

        capturedImagesList.add(embeddedImage);
        ImageView imageView = createImageView(embeddedImage.image());
        CapturedImages.getChildren().add(imageView);
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
            webCamThread = new Thread(this::processCameraFrames);
            webCamThread.setDaemon(true);
            webCamThread.start();
        }).start();
    }

    private void processCameraFrames() {
        Mat frame = new Mat();
        while (running) {
            if (camera.read(frame)) {
                Mat clonedFrame = frame.clone();
                processFrame(clonedFrame);
                Image image = JavaFxImageConverter.toImage(clonedFrame);
                Platform.runLater(() -> FrameView.setImage(image));
            }
        }
    }

    private void processFrame(Mat frame) {
        Rect detectedFace = faceDetector.detectFace(frame);
        if (detectedFace != null) {
            Mat croppedFace = faceCropper.cropFace(frame, detectedFace);
            detectedFaceImage.set(croppedFace);
            drawRect(frame, detectedFace);
        }
    }

    private ImageView createImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.fitWidthProperty().bind(CapturedImages.widthProperty().add(-20));
        imageView.fitHeightProperty().bind(CapturedImages.widthProperty().add(-20));
        imageView.setCursor(Cursor.HAND);
        int index = capturedImagesList.size();
        imageView.setOnMouseClicked(_ -> {
            capturedImagesList.remove(index);
            CapturedImages.getChildren().remove(imageView);
        });
        return imageView;
    }

    private static void drawRect(Mat frame, Rect detectedFace) {
        Imgproc.rectangle(frame, detectedFace.tl(), detectedFace.br(), new Scalar(255, 0, 0), 1);
    }

    @Override
    public void close() {
        try {
            Platform.runLater(() -> {
                StateStorage.capturedImagesProperty.set(capturedImagesList);
                FrameView.setImage(null);
            });
            running = false;
            if (webCamThread != null) webCamThread.join();
            if (camera != null) camera.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}