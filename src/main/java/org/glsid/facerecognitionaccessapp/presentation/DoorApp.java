package org.glsid.facerecognitionaccessapp.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nu.pattern.OpenCV;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;
import org.glsid.facerecognitionaccessapp.presentation.views.DoorView;

import java.io.IOException;

public class DoorApp extends Application {
    private DoorView doorView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        OpenCV.loadLocally();
        doorView = new DoorView();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = (Parent) doorView.getRoot();
        Scene scene = new Scene(root, Views.WIDTH, Views.HEIGHT);

        primaryStage.setTitle("Front Door App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        doorView.close();
    }
}
