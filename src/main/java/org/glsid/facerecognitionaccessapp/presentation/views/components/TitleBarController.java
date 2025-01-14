package org.glsid.facerecognitionaccessapp.presentation.views.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.glsid.facerecognitionaccessapp.Constants.Styles;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TitleBarController implements Initializable {

    @FXML
    private Button closeBtn;

    @FXML
    private Button minBtn;

    @FXML
    private Button restMaxBtn;

    @FXML
    private HBox root;

    @FXML
    private Label windowTitle;

    Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource(Styles.TITLE_BAR)).toExternalForm());
        HBox.setHgrow(root, Priority.ALWAYS);

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            getStage().setX(event.getScreenX() - xOffset);
            getStage().setY(event.getScreenY() - yOffset);
        });
    }



    @FXML
    private void closeWindow() {
        getStage().close();
    }

    @FXML
    private void maximizeWindow() {
        getStage().setMaximized(!getStage().isMaximized());
    }
    @FXML
    private void minimizeWindow() {
        getStage().setIconified(true);
    }

    Stage getStage() {
        if (stage == null) {
            stage = (Stage) root.getScene().getWindow();
        }
        return stage;
    }
}
