package org.glsid.facerecognitionaccessapp.presentation.views;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nu.pattern.OpenCV;
import org.glsid.facerecognitionaccessapp.presentation.components.Demo;

import java.io.IOException;

public class DemoFaceRecognition extends Application {
    static {
        OpenCV.loadLocally();
    }

    private static final Demo demoComponent = new Demo();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = (Parent) demoComponent.getRoot();
        primaryStage.setTitle("Face Recognition Access App");
        primaryStage.setScene(new Scene(root, 1280, 900));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        demoComponent.close();
    }
}
