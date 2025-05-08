package org.glsid.facerecognitionaccessapp.presentation.router;

import java.io.IOException;
import java.util.Stack;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class Router {
    private final Stack<RoutableView> routeStack = new Stack<>();
    private final Pane slot;

    public Router(Pane slot) {
        this.slot = slot;
    }

    public void go(String route, RouteData data) {
        routeStack.clear();
        slot.getChildren().clear();
        loadAndDisplayView(route, data);
    }
    public void go(String route) {
        go(route, new RouteData());
    }

    public void pushReplacement(String route, RouteData data) {
        routeStack.pop();
        slot.getChildren().removeLast();
        loadAndDisplayView(route, data);
    }
    public void pushReplacement(String route) {
        pushReplacement(route, new RouteData());
    }

    public void push(String route, RouteData data) {
        loadAndDisplayView(route, data);
    }
    public void push(String route) {
        push(route, new RouteData());
    }

    public void pop() {
        if (routeStack.size() > 1) {
            RoutableView view = routeStack.peek();
            view.close();
            routeStack.pop();
            slot.getChildren().removeLast();
        }
    }

    private void loadAndDisplayView(String fxmlFile, RouteData data) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            loader.getController();
            Node root = loader.load();
            RoutableView view = loader.getController();

            data.setRouter(this);
            root.setUserData(data);

            routeStack.push(view);
            slot.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
