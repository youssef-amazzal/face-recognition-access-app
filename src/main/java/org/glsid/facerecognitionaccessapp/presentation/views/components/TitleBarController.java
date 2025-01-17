package org.glsid.facerecognitionaccessapp.presentation.views.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Styles;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;
import org.glsid.facerecognitionaccessapp.presentation.router.Router;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TitleBarController implements Initializable {


    @FXML
    private ToggleButton analyticsBtn;

    @FXML
    private Button closeBtn;

    @FXML
    private ToggleButton logsBtn;

    @FXML
    private Button minBtn;

    @FXML
    private ToggleButton pfpBtn;

    @FXML
    private Button restMaxBtn;

    @FXML
    private ToggleButton roomsBtn;

    @FXML
    private HBox root;

    @FXML
    private ToggleButton usersBtn;

    @FXML
    private Label windowTitle;

    private final ToggleGroup routesGroup = new ToggleGroup();
    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    Router router;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource(Styles.TITLE_BAR)).toExternalForm());
        HBox.setHgrow(root, Priority.ALWAYS);

        initDraggability();
        initRouting();
        initPfpBtn();

        Rectangle rect = new Rectangle(Views.WIDTH,Views.HEIGHT);
        rect.setArcHeight(10.0);
        rect.setArcWidth(10.0);
        root.setClip(rect);


    }

    void initPfpBtn() {
        // Unfortunatetly, this is the only way to make sure the image also circulat not just the button
        var width = pfpBtn.getPrefWidth();
        var height = pfpBtn.getPrefHeight();
        pfpBtn.setShape(new Circle(width/ 2));
        pfpBtn.setClip(new Circle(width / 2, height/ 2, width / 2));
    }

    void initDraggability() {
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            getStage().setX(event.getScreenX() - xOffset);
            getStage().setY(event.getScreenY() - yOffset);
        });
    }

    void initRouting() {
        analyticsBtn.setToggleGroup(routesGroup);
        logsBtn.setToggleGroup(routesGroup);
        roomsBtn.setToggleGroup(routesGroup);
        usersBtn.setToggleGroup(routesGroup);

        routesGroup.selectedToggleProperty().addListener((_, oldVal, newVal) -> {
            if (newVal == null) oldVal.setSelected(true);
        });

        analyticsBtn.setOnAction(_ -> getRouter().go(Views.ANALYTICS_VIEW));
        roomsBtn.setOnAction(_ -> getRouter().go(Views.ROOMS_VIEW));
        usersBtn.setOnAction(_ -> getRouter().go(Views.USERS_VIEW));

        root.sceneProperty().addListener((_ -> analyticsBtn.fire()));
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

    private Router getRouter() {
        if (router == null) {
            router = (Router) root.getUserData();
        }
        return router;
    }
}
