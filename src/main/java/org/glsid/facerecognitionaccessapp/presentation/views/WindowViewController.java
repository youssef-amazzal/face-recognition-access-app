package org.glsid.facerecognitionaccessapp.presentation.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Styles;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;
import org.glsid.facerecognitionaccessapp.presentation.components.TitleBar;
import org.glsid.facerecognitionaccessapp.presentation.events.ui.UiEvent;
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
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource(Styles.COMMON)).toExternalForm());
        initTitleBar();
    }

    void initTitleBar() {
        root.sceneProperty().addListener((_, _, newScene) -> newScene.windowProperty().addListener((_, _, newWindow) -> {
            try {
                Router router = new Router(mainSlot);
                Stage stage = (Stage) newWindow;

                Node titleBar = new TitleBar(stage, router).getRoot();
                titleBar.setUserData(router);
                titleBarSlot.getChildren().add(titleBar);

                root.addEventHandler(UiEvent.TITLE_BAR_VISIBILITY_EVENT, event -> {
                    titleBarSlot.setVisible(event.isVisible());
                    titleBarSlot.setManaged(event.isVisible());
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }
}
