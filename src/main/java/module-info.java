module org.glsid.facerecognitionaccessapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.materialdesign;
    requires java.sql;

    exports org.glsid.facerecognitionaccessapp.presentation;
    exports org.glsid.facerecognitionaccessapp.presentation.views;
    exports org.glsid.facerecognitionaccessapp.presentation.views.components;
    exports org.glsid.facerecognitionaccessapp.presentation.Constants;
    exports org.glsid.facerecognitionaccessapp.core.dto;
    exports org.glsid.facerecognitionaccessapp.core.entities;
    exports org.glsid.facerecognitionaccessapp.infrastructure.db.sqlite;

    opens org.glsid.facerecognitionaccessapp.presentation to javafx.fxml;
    opens org.glsid.facerecognitionaccessapp.presentation.views to javafx.fxml;
    opens org.glsid.facerecognitionaccessapp.presentation.views.components to javafx.fxml;
    opens org.glsid.facerecognitionaccessapp.presentation.Constants to javafx.fxml;
}