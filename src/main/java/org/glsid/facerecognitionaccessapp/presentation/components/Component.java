package org.glsid.facerecognitionaccessapp.presentation.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;

public abstract class Component {
    private final String fxmlPath;
    private final String styleSheetPath;

    protected Component(String fxmlPath) {
        this.fxmlPath = fxmlPath;
        this.styleSheetPath = null;
    }
    protected Component(String fxmlPath, String styleSheetPath) {
        this.fxmlPath = fxmlPath;
        this.styleSheetPath = styleSheetPath;
    }


    public Node getRoot() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setController(this);
        Parent root = loader.load();
        addStyleSheet(root);
        return root;
    }

    private void addStyleSheet(Parent root) {
        if (styleSheetPath != null) root.getStylesheets().add(Objects.requireNonNull(getClass().getResource(styleSheetPath)).toExternalForm());
    }
}

