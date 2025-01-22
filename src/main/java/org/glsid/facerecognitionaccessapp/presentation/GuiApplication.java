package org.glsid.facerecognitionaccessapp.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nu.pattern.OpenCV;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;

public class GuiApplication extends Application {
    @Override
    public void init() throws Exception {
        super.init();
        OpenCV.loadLocally();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(GuiApplication.class.getResource(Views.WINDOW_VIEW));
        Parent root = loader.load();
        Scene scene = new Scene(root, Views.WIDTH, Views.HEIGHT);

        configWindow(primaryStage, root, scene);
        primaryStage.show();
    }

    private static void configWindow(Stage primaryStage, Parent root, Scene scene) {
        Rectangle rect = new Rectangle(Views.WIDTH, Views.HEIGHT);
//        rect.widthProperty().bind(scene.widthProperty());
//        rect.widthProperty().bind(scene.widthProperty());
        rect.setArcHeight(20);
        rect.setArcWidth(20);
        root.setClip(rect);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Face Recognition Access App");
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
