package org.glsid.facerecognitionaccessapp.presentation.views;

import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.glsid.facerecognitionaccessapp.application.usecases.WhoIsIt;
import org.glsid.facerecognitionaccessapp.core.entities.User;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.IFaceCropper;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.IFaceDetector;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.IFaceFeatureExtractor;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.IFacePreprocessor;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl.FaceCropper;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl.FaceNetExtractor;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl.FaceNetPreprocessor;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl.HaarCascadeDetector;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Styles;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;
import org.glsid.facerecognitionaccessapp.presentation.components.Component;
import org.glsid.facerecognitionaccessapp.presentation.utils.JavaFxImageConverter;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.net.URL;
import java.util.ResourceBundle;

public class DoorView extends Component implements Initializable {
    @FXML private StackPane root;
    @FXML private ImageView FrameView;
    @FXML private Button OpenDoorButton;
    @FXML private HBox Toast;
    @FXML private Label ToastMessage;
    @FXML private Label ToastTitle;

    private final IFaceDetector faceDetector = new HaarCascadeDetector();
    private static final IFaceCropper faceCropper = new FaceCropper();
    private final IFacePreprocessor facePreprocessor = new FaceNetPreprocessor();
    private final IFaceFeatureExtractor faceFeatureExtractor = new FaceNetExtractor();
    private final WhoIsIt whoIsIt = new WhoIsIt();

    private VideoCapture camera;
    private Thread webCamThread;
    private volatile boolean running;

    private User user;
    private float[] currentFeatures;

    public DoorView() {
        super(Views.DOOR_VIEW, Styles.COMMON);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configWindow();
        startCamera();
        OpenDoorButton.setOnAction(_ -> openDoor());
        OpenDoorButton.setDisable(true); // Initially disable the button
        Toast.setVisible(false);
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
            running = true;
            webCamThread = new Thread(() -> {
                Mat frame = new Mat();
                while (running) {
                    if (camera.read(frame)) {
                        Mat clonedFrame = frame.clone();
                        Rect detectedFace = faceDetector.detectFace(clonedFrame);
                        if (detectedFace != null) {
                            Mat preprocessedFace = facePreprocessor.preprocessFace(faceCropper.cropFace(frame.clone(), detectedFace));
                            currentFeatures = faceFeatureExtractor.extractFeatures(preprocessedFace);
                            Imgproc.rectangle(clonedFrame, detectedFace.tl(), detectedFace.br(), new Scalar(255, 0, 0), 1);
                        } else {
                            currentFeatures = null;
                        }
                        Image imageToShow = JavaFxImageConverter.toImage(clonedFrame);
                        Platform.runLater(() -> {
                            FrameView.setImage(imageToShow);
                            OpenDoorButton.setDisable(currentFeatures == null); // Enable/disable button based on currentFeatures
                        });
                    }
                }
            });
            webCamThread.setDaemon(true);
            webCamThread.start();
        }).start();
    }

    public void close() {
        running = false;
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

    public void showToast(String message, String title, PseudoClass pseudoClass) {
        ToastMessage.setText(message);
        ToastTitle.setText(title);
        Toast.setVisible(true);
        Toast.pseudoClassStateChanged(PseudoClass.getPseudoClass("failure"), false);
        Toast.pseudoClassStateChanged(PseudoClass.getPseudoClass("success"), false);
        Toast.pseudoClassStateChanged(pseudoClass, true);
        new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> Toast.setVisible(false));
        }).start();
    }

    public void openDoor() {
        if (currentFeatures == null) {
            showToast("No face detected", "Error", PseudoClass.getPseudoClass("failure"));
            return;
        }

        user = whoIsIt.find(currentFeatures);

        if (user == null) {
            showToast("User not found", "Error", PseudoClass.getPseudoClass("failure"));
            return;
        }

        showToast("Welcome " + user.firstName() + " " + user.lastName(), "Success", PseudoClass.getPseudoClass("success"));
    }
}