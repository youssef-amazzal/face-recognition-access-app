package org.glsid.facerecognitionaccessapp.presentation.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;

public abstract class Component {
    private final String fxmlPath;
    private final String styleSheetPath;

    @FXML private Parent root;

    public Component(String fxmlPath) {
        this.fxmlPath = fxmlPath;
        this.styleSheetPath = null;
    }
    public Component(String fxmlPath, String styleSheetPath) {
        this.fxmlPath = fxmlPath;
        this.styleSheetPath = styleSheetPath;
    }


    public Node getRoot() throws IOException {
        if (root != null) return root;

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setController(this);
        root = loader.load();
        addStyleSheet(root);
        return root;
    }

    private void addStyleSheet(Parent root) {
        if (styleSheetPath != null) root.getStylesheets().add(Objects.requireNonNull(getClass().getResource(styleSheetPath)).toExternalForm());
    }
}

