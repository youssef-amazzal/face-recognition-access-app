package org.glsid.facerecognitionaccessapp.presentation.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Stack;


public class InterfaceManager {
    public static final Stack<Node> searchPageStack = new Stack<>();
    public static final Stack<Node> homePageStack = new Stack<>();

    private static Scene currentScene;
    private static Stage currentStage;

    public static void clearAllCollections() {
//        searchPageStack.clear();
        homePageStack.clear();
    }

    public static Node loadPage (String path) {
        Parent page = null;
        StackPane container = (StackPane) currentScene.lookup("#container");
        try {
            FXMLLoader pageLoader = new FXMLLoader(InterfaceManager.class.getResource(path));
            page = pageLoader.load();
//            VBox.setVgrow(page, Priority.ALWAYS);
            page.setUserData(pageLoader.getController());

            container.getChildren().clear();
            container.getChildren().add(page);

            getCurrentStack().push(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    public static Node loadPage (String path, Pane container) {
        Parent page = null;
        try {
            FXMLLoader pageLoader = new FXMLLoader(InterfaceManager.class.getResource(path));
            page = pageLoader.load();
//            VBox.setVgrow(page, Priority.ALWAYS);
            page.setUserData(pageLoader.getController());

            container.getChildren().clear();
            container.getChildren().add(page);

            getCurrentStack().push(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    public static void loadPage (Stack<Node> pageList) {
        StackPane container = (StackPane) currentScene.lookup("#container");
        container.getChildren().clear();
        container.getChildren().addAll(pageList);
    }

    public static void loadPage (Node page) {
        StackPane container = (StackPane) currentScene.lookup("#container");
        container.getChildren().clear();
        container.getChildren().add(page);
    }

    public static void closePages() {
        StackPane container = (StackPane) currentScene.lookup("#container");
        Node page = getCurrentStack().firstElement();

        getCurrentStack().clear();
        container.getChildren().clear();

        getCurrentStack().push(page);
        switchPage();

    }

    public static void goBack() {
        StackPane container = (StackPane) currentScene.lookup("#container");
        int recentChild = container.getChildren().size() - 1;
        container.getChildren().remove(recentChild);
        popPage();
    }

    public static Stack<Node> getCurrentStack() {
//        return ((NavButton) NavBarController.navigationBarGroup.getSelectedToggle()).getStack();
        return null;
    }

    public static void popPage() {
        getCurrentStack().pop();
    }

    public static void pushPage(Node page) {
        getCurrentStack().push(page);
    }

    public static void switchPage() {
//        ((NavButton) NavBarController.navigationBarGroup.getSelectedToggle()).openPage();
    }

    public static void startApp(Stage stage, String pagePath) {
        InterfaceManager.clearAllCollections();

        if (currentStage != null) {
            currentStage.close();
        }

        currentStage = stage;

        try {
            Parent root = FXMLLoader.load(InterfaceManager.class.getResource(pagePath));

            currentScene = new Scene(root);

            Rectangle clip = new Rectangle();
            clip.widthProperty().bind(((Pane) root).widthProperty());
            clip.heightProperty().bind(((Pane) root).heightProperty());
            clip.setArcHeight(20);
            clip.setArcWidth(20);
            root.setClip(clip);


            currentScene.setFill(Color.TRANSPARENT);
            currentStage.setScene(currentScene);
            currentStage.initStyle(StageStyle.TRANSPARENT);
            currentStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static FXMLLoader getLoader(String fxmlPath) {
        return new FXMLLoader(InterfaceManager.class.getResource(fxmlPath));
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static Stage getCurrentStage() {
        return currentStage;
    }
}
