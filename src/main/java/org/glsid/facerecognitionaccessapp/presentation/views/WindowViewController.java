package org.glsid.facerecognitionaccessapp.presentation.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.glsid.facerecognitionaccessapp.Constants.Styles;
import org.glsid.facerecognitionaccessapp.Constants.Views;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class WindowViewController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private HBox titleBarContainer;

    @FXML
    private HBox viewContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            root.getStylesheets().add(Objects.requireNonNull(getClass().getResource(Styles.COMMON)).toExternalForm());
            FXMLLoader tBarLoader = new FXMLLoader(getClass().getResource(Views.TITLE_BAR));
            FXMLLoader faceCamLoader = new FXMLLoader(getClass().getResource(Views.FACE_CAM_VIEW));

            titleBarContainer.getChildren().add(tBarLoader.load());
//            viewContainer.getChildren().add(faceCamLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
