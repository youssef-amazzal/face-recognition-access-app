package org.glsid.facerecognitionaccessapp.presentation.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Styles;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;
import org.glsid.facerecognitionaccessapp.presentation.router.MainRouteData;
import org.glsid.facerecognitionaccessapp.presentation.router.Router;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

// TODO: Deconstruct into smaller components, this class is a violation to SRP
public class TitleBar extends Component implements Initializable {
    private final ToggleGroup routesGroup = new ToggleGroup();
    private final Router router;
    private final Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    private final Stack<String> backStack = new Stack<>(); // TODO: this a workaround, fix the problem with a better solution

    @FXML private HBox root;
    @FXML private Button BackButton;
    @FXML private Label AppName;

    @FXML private HBox NavBar;
    @FXML private ToggleButton AnalyticsButton;
    @FXML private ToggleButton LogsButton;
    @FXML private ToggleButton RoomsButton;
    @FXML private ToggleButton UsersButton;

    @FXML private ToggleButton PfpButton;
    @FXML private HBox WindowActions;
    @FXML private Button MinButton;
    @FXML private Button RestMaxButton;
    @FXML private Button CloseButton;

    public TitleBar(Stage stage, Router router) {
        super(Views.TITLE_BAR, Styles.TITLE_BAR);
        this.router = router;
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HBox.setHgrow(root, Priority.ALWAYS);

        initDraggability();
        initNavBar();
        initPfpBtn();
        BackButton.setOnAction(_ -> {
            hideBackButton();
            router.pop();
        });

        Rectangle rect = new Rectangle(Views.WIDTH,Views.HEIGHT);
        rect.setArcHeight(10.0);
        rect.setArcWidth(10.0);
        root.setClip(rect);

        hideComponent(BackButton);
    }

    public void showBackButton(String text) {
        backStack.push(text);
        BackButton.setText(text);
        showComponent(BackButton);
        hideComponent(NavBar);
        hideComponent(AppName);
        hideComponent(PfpButton);
    }

    private void hideBackButton() {
        backStack.pop();
        if (!backStack.isEmpty()) {
            BackButton.setText(backStack.peek());
            return;
        }
        hideComponent(BackButton);
        showComponent(NavBar);
        showComponent(AppName);
        showComponent(PfpButton);
    }

    private void initPfpBtn() {
        // Unfortunately, this is the only way to make sure the image also circulate not just the button
        var width = PfpButton.getPrefWidth();
        var height = PfpButton.getPrefHeight();
        PfpButton.setShape(new Circle(width/ 2));
        PfpButton.setClip(new Circle(width / 2, height/ 2, width / 2));
    }

    private void initDraggability() {
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    private void initNavBar() {
        AnalyticsButton.setToggleGroup(routesGroup);
        LogsButton.setToggleGroup(routesGroup);
        RoomsButton.setToggleGroup(routesGroup);
        UsersButton.setToggleGroup(routesGroup);

        routesGroup.selectedToggleProperty().addListener((_, oldVal, newVal) -> {
            if (newVal == null) oldVal.setSelected(true);
        });

        AnalyticsButton.setOnAction(_ -> router.go(Views.ANALYTICS_VIEW, new MainRouteData(this)));
        RoomsButton.setOnAction(_ -> router.go(Views.ROOMS_VIEW, new MainRouteData(this)));
        UsersButton.setOnAction(_ -> router.go(Views.USERS_VIEW, new MainRouteData(this)));

        root.sceneProperty().addListener((_ -> AnalyticsButton.fire()));
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


    private void hideComponent(Node component) {
        component.setVisible(false);
        component.setManaged(false);
    }

    private void showComponent(Node component) {
        component.setVisible(true);
        component.setManaged(true);
    }

}
