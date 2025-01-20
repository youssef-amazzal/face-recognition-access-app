package org.glsid.facerecognitionaccessapp.presentation.components.forms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;
import org.glsid.facerecognitionaccessapp.presentation.components.Component;
import org.glsid.facerecognitionaccessapp.presentation.router.MainRouteData;

import java.net.URL;
import java.util.ResourceBundle;

public class UserImageForm extends Component implements Initializable {
    @FXML private HBox CaptureButton;
    @FXML private Button SelectImageButton;
    private final MainRouteData data;

    public UserImageForm(MainRouteData data) {
        super(Views.USER_IMAGE_FORM);
        this.data = data;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CaptureButton.setOnMouseClicked(_ -> {
            data.getTitleBar().showBackButton("Back to user creation");
            data.getRouter().push(Views.FACE_CAM_VIEW);
        });
    }
}
