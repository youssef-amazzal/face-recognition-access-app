package org.glsid.facerecognitionaccessapp.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.glsid.facerecognitionaccessapp.Constants.Styles;
import org.glsid.facerecognitionaccessapp.Constants.Views;

import java.util.Objects;

public class GuiApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Application.setUserAgentStylesheet(Objects.requireNonNull(getClass().getResource(Styles.DEFAULT)).toExternalForm());
        FXMLLoader loader = new FXMLLoader(GuiApplication.class.getResource(Views.WINDOW_VIEW));
        Parent root = loader.load();
        Scene scene = new Scene(root, Views.WIDTH, Views.HEIGHT);
        Rectangle rect = new Rectangle(Views.WIDTH,Views.HEIGHT);
        rect.setArcHeight(10.0);
        rect.setArcWidth(10.0);
        root.setClip(rect);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Face Recognition Access App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
