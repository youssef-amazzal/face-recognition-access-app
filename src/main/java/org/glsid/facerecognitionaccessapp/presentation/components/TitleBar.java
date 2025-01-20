package org.glsid.facerecognitionaccessapp.presentation.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Styles;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;
import org.glsid.facerecognitionaccessapp.presentation.router.Router;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TitleBar extends Component implements Initializable {
    private final ToggleGroup routesGroup = new ToggleGroup();
    private final Router router;
    private final Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML private ToggleButton analyticsBtn;
    @FXML private ToggleButton logsBtn;
    @FXML private ToggleButton pfpBtn;
    @FXML private ToggleButton roomsBtn;
    @FXML private HBox root;
    @FXML private ToggleButton usersBtn;
    @FXML private Pane windowControlsSlot;
    @FXML private Label windowTitle;

    public TitleBar(Stage stage, Router router) {
        super(Views.TITLE_BAR, Styles.TITLE_BAR);
        this.router = router;
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HBox.setHgrow(root, Priority.ALWAYS);

        initDraggability();
        initRouting();
        initPfpBtn();

        Rectangle rect = new Rectangle(Views.WIDTH,Views.HEIGHT);
        rect.setArcHeight(10.0);
        rect.setArcWidth(10.0);
        root.setClip(rect);

        try {
            WindowControls windowControls = new WindowControls(stage);
            windowControlsSlot.getChildren().add(windowControls.getRoot());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
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

        analyticsBtn.setOnAction(_ -> router.go(Views.ANALYTICS_VIEW));
        roomsBtn.setOnAction(_ -> router.go(Views.ROOMS_VIEW));
        usersBtn.setOnAction(_ -> router.go(Views.USERS_VIEW));

        root.sceneProperty().addListener((_ -> analyticsBtn.fire()));
    }
}
