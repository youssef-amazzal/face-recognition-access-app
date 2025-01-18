package org.glsid.facerecognitionaccessapp.presentation.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;
import org.glsid.facerecognitionaccessapp.presentation.router.RouteData;
import org.glsid.facerecognitionaccessapp.presentation.router.Router;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersViewController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private Button addUserBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addUserBtn.setOnAction(_ -> {
            RouteData data = (RouteData) root.getUserData();
            Router router = data.getRouter();
            router.push(Views.USER_CREATION_VIEW);
        });
    }
}
