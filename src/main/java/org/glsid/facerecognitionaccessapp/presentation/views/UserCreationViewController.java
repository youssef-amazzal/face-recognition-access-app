package org.glsid.facerecognitionaccessapp.presentation.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import org.glsid.facerecognitionaccessapp.presentation.events.ui.UiEvent;
import org.glsid.facerecognitionaccessapp.presentation.router.RouteData;

import java.net.URL;
import java.util.ResourceBundle;

public class UserCreationViewController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private Button backBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.sceneProperty().addListener((_, _, _) -> root.fireEvent(new UiEvent(false)));
        backBtn.setOnAction(_ -> {
            root.fireEvent(new UiEvent(true));
            RouteData data = (RouteData) root.getUserData();
            data.getRouter().pop();
        });
    }
}
