package org.glsid.facerecognitionaccessapp.presentation.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;

import java.io.IOException;

public class WindowControlsComponent implements IComponent {

    private Stage stage;

    @FXML private HBox root;
    @FXML private Button closeBtn;
    @FXML private Button minBtn;
    @FXML private Button restMaxBtn;

    @FXML private void closeWindow() {
        getStage().close();
    }
    @FXML private void maximizeWindow() {
        getStage().setMaximized(!getStage().isMaximized());
    }
    @FXML private void minimizeWindow() {
        getStage().setIconified(true);
    }

    private Stage getStage() {
        if (stage == null) {
            stage = (Stage) root.getScene().getWindow();
        }
        return stage;
    }

    @Override
    public Node getRoot() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Views.WINDOW_CONTROLS));
        loader.setController(this);
        return loader.load();
    }
}
