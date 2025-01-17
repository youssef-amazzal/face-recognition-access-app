package org.glsid.facerecognitionaccessapp.presentation.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Styles;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;
import org.glsid.facerecognitionaccessapp.presentation.router.Router;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class WindowViewController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private HBox titleBarSlot;

    @FXML
    private VBox mainSlot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            root.getStylesheets().add(Objects.requireNonNull(getClass().getResource(Styles.COMMON)).toExternalForm());

            Router router = new Router(mainSlot);

            FXMLLoader tBarLoader = new FXMLLoader(getClass().getResource(Views.TITLE_BAR));
            Node titleBar = tBarLoader.load();
            titleBar.setUserData(router);
            titleBarSlot.getChildren().add(titleBar);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
