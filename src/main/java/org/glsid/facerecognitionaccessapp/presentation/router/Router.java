package org.glsid.facerecognitionaccessapp.presentation.router;

import java.io.IOException;
import java.util.Stack;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.glsid.facerecognitionaccessapp.presentation.exceptions.ViewNotFound;

public class Router {
//    private final static Map<Parent, Router> managerInstances = new HashMap<>();
    private final Stack<RoutableView> routeStack = new Stack<>();
//    private final Stack<RouteData> dataStack = new Stack<>();
    private final StringProperty activeRoute = new SimpleStringProperty();
    private final Pane slot;

    public Router(Pane slot) {
        this.slot = slot;
    }
//    public static Router getInstance(Pane slot) {
//        if (!managerInstances.containsKey(slot)) {
//            managerInstances.put(slot, new Router(slot));
//        }
//
//        return managerInstances.get(slot);
//    }


    public void go(String route, RouteData data) {
        routeStack.clear();
        loadAndDisplayView(route, data);
    }
    public void go(String route) {
        go(route, new RouteData());
    }

    public void pushReplacement(String route, RouteData data) {
        routeStack.pop();
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
            RoutableView view = routeStack.pop();
            view.close();
//            dataStack.pop();
            slot.getChildren().setAll(routeStack.peek().getRoot());
        }
    }

    public StringProperty activeRouteProperty() {
        return activeRoute;
    }

//    public <T extends RouteData> T getActiveRouteData() {
//        if (dataStack.isEmpty()) return null;
//        return (T) dataStack.peek();
//    }

    private void loadAndDisplayView(String fxmlFile, RouteData data) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            loader.getController();
            Node root = loader.load();
            RoutableView view = loader.getController();

            data.setRouter(this);
            root.setUserData(data);

            routeStack.push(view);
//            dataStack.push(data);
            slot.getChildren().setAll(root);

            activeRoute.set(fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
