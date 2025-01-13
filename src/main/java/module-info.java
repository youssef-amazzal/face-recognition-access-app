module org.glsid.facerecognitionaccessapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens org.glsid.facerecognitionaccessapp to javafx.fxml;
    exports org.glsid.facerecognitionaccessapp.presentation;
    opens org.glsid.facerecognitionaccessapp.presentation to javafx.fxml;
    exports org.glsid.facerecognitionaccessapp.presentation.controllers;
    opens org.glsid.facerecognitionaccessapp.presentation.controllers to javafx.fxml;
}