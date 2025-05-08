package org.glsid.facerecognitionaccessapp.presentation.router;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;

public abstract class RoutableView {
    @FXML protected Parent root;

    protected RouteData getRouteData() {
        return (RouteData) root.getUserData();
    }

    protected Router getRouter() {
        return getRouteData().getRouter();
    }
    public Node getRoot() {return root;}
    public void close() {}
}
