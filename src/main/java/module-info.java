module org.glsid.facerecognitionaccessapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;

    opens org.glsid.facerecognitionaccessapp to javafx.fxml;
    exports org.glsid.facerecognitionaccessapp;
}