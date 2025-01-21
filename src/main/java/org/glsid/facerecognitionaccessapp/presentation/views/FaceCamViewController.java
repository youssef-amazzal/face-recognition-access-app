package org.glsid.facerecognitionaccessapp.presentation.views;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.glsid.facerecognitionaccessapp.presentation.router.RoutableView;
import org.glsid.facerecognitionaccessapp.core.utils.face_detection.HaarCascadeDetector;
import org.glsid.facerecognitionaccessapp.core.utils.face_detection.IFaceDetector;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;

public class FaceCamViewController extends RoutableView implements Initializable {

    @FXML private StackPane root;
    @FXML private ImageView FrameView;
    @FXML private ImageView CapturedImage;
    @FXML private HBox CapturedImageSlot;

    private static volatile Thread webCamThread;
    private final IFaceDetector faceDetector = new HaarCascadeDetector();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FrameView.fitHeightProperty().bind(root.heightProperty());
        FrameView.fitWidthProperty().bind(root.widthProperty());

        webCamThread = new Thread(() -> {
            final int DEFAULT_WEBCAM = 0;
            try (
                    OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(DEFAULT_WEBCAM);
                    JavaFXFrameConverter converterJavaFX = new JavaFXFrameConverter();
                    Java2DFrameConverter converter2D = new Java2DFrameConverter();
                    OpenCVFrameConverter.ToMat toMatConverter = new OpenCVFrameConverter.ToMat()
            ) {
                grabber.start();

                while (!Thread.interrupted()) {
                    final Frame frame = grabber.grab();

                    if (frame == null) break;
                    if (frame.image != null) {
                        final Frame imageFrame = frame.clone();
                        final List<Rect> detectedFaces = faceDetector.apply(imageFrame);
                        for (Rect face : detectedFaces) {
                            rectangle(toMatConverter.convertToMat(imageFrame), face, Scalar.RED);
                        }

                        Platform.runLater(() -> {
                            final Image image = converterJavaFX.convert(imageFrame);
                            FrameView.setImage(image);
                            imageFrame.close();
                        });
                    }

                    if (grabber.getFrameNumber() > 0) Thread.sleep(1000 / grabber.getFrameNumber());
                    else Thread.sleep(1000 / 30);
                }

                grabber.stop();
                grabber.release();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            } catch (InterruptedException _) {}
        });

        webCamThread.setDaemon(true);
        webCamThread.start();
    }

    @Override
    public void close() {
        if (webCamThread != null) {
            webCamThread.interrupt();
        }
    }
}