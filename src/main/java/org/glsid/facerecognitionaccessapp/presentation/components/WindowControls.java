package org.glsid.facerecognitionaccessapp.presentation.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Styles;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;

import java.net.URL;
import java.util.ResourceBundle;

public class WindowControls extends Component implements Initializable {
    private final Stage stage;
    @FXML private HBox root;
    @FXML private Button closeBtn;
    @FXML private Button minBtn;
    @FXML private Button restMaxBtn;

    public WindowControls(Stage stage) {
        super(Views.WINDOW_CONTROLS, Styles.WINDOW_CONTORLS);
        this.stage = stage;
    }

    @FXML private void closeWindow() {
        stage.close();
    }
    @FXML private void maximizeWindow() {
        stage.setMaximized(!stage.isMaximized());
    }
    @FXML private void minimizeWindow() {
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
