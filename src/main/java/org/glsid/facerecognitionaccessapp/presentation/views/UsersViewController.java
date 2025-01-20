package org.glsid.facerecognitionaccessapp.presentation.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;
import org.glsid.facerecognitionaccessapp.presentation.router.MainRouteData;
import org.glsid.facerecognitionaccessapp.presentation.router.RoutableView;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersViewController extends RoutableView implements Initializable {

    @FXML
    private Button addUserBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addUserBtn.setOnAction(_ -> {
            MainRouteData data = (MainRouteData) root.getUserData();
            data.getTitleBar().showBackButton("Back to users");
            getRouter().push(Views.USER_CREATION_VIEW);
        });
    }
}
