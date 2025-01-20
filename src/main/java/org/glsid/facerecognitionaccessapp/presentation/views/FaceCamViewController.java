package org.glsid.facerecognitionaccessapp.presentation.views;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.JavaFXFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.glsid.facerecognitionaccessapp.presentation.router.RoutableView;

import java.net.URL;
import java.util.ResourceBundle;

public class FaceCamViewController extends RoutableView implements Initializable {

    @FXML private StackPane root;
    @FXML private ImageView FrameView;
    private static volatile Thread webCamThread;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FrameView.fitHeightProperty().bind(root.heightProperty());
        FrameView.fitWidthProperty().bind(root.widthProperty());

        webCamThread = new Thread(() -> {
            final int DEFAULT_WEBCAM = 0;
            try (
                    OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(DEFAULT_WEBCAM);
                    JavaFXFrameConverter converter = new JavaFXFrameConverter()
            ) {
                grabber.start();
                System.out.println("Frame rate: " + grabber.getFrameRate() + " FPS");

                while (!Thread.interrupted()) {
                    final Frame frame = grabber.grab();

                    if (frame == null) break;
                    if (frame.image != null) {
                        final Frame imageFrame = frame.clone();

                        Platform.runLater(() -> {
                            final Image image = converter.convert(imageFrame);
                            FrameView.setImage(image);
                            imageFrame.close();
                        });
                    }

                    Thread.sleep(1000 / grabber.getFrameNumber());
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

