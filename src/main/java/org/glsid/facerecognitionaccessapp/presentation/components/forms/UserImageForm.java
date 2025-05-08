package org.glsid.facerecognitionaccessapp.presentation.components.forms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import org.glsid.facerecognitionaccessapp.core.dto.ui.EmbeddedImage;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.IFaceFeatureExtractor;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.IFacePreprocessor;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl.FaceNetExtractor;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl.FaceNetPreprocessor;
import org.glsid.facerecognitionaccessapp.presentation.utils.JavaFxImageConverter;
import org.glsid.facerecognitionaccessapp.presentation.utils.StateStorage;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.IFaceCropper;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.IFaceDetector;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl.FaceCropper;
import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl.HaarCascadeDetector;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;
import org.glsid.facerecognitionaccessapp.presentation.components.Component;
import org.glsid.facerecognitionaccessapp.presentation.router.MainRouteData;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class UserImageForm extends Component implements Initializable {
    @FXML private VBox root;
    @FXML private HBox CaptureButton;
    @FXML private Button SelectImageButton;
    @FXML private StackPane DragAndDropRegion;
    @FXML private FlowPane ImagesSlot;

    private final ObservableList<EmbeddedImage> images = FXCollections.observableArrayList();
    private final MainRouteData data;
    private final IFaceDetector faceDetector = new HaarCascadeDetector();
    private final IFaceCropper faceCropper = new FaceCropper();
    private final IFacePreprocessor facePreprocessor = new FaceNetPreprocessor();
    private final IFaceFeatureExtractor faceFeatureExtractor = new FaceNetExtractor();

    public UserImageForm(MainRouteData data) {
        super(Views.USER_IMAGE_FORM);
        this.data = data;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CaptureButton.setOnMouseClicked(_ -> {
            data.getTitleBar().showBackButton("Back to user creation");
            data.getRouter().push(Views.FACE_CAM_VIEW);
        });

        SelectImageButton.setOnAction(_ -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(SelectImageButton.getScene().getWindow());
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                Mat matImage = JavaFxImageConverter.toMat(image);
                Rect detectedFace = faceDetector.detectFace(matImage);
                if (detectedFace != null) {

                    Mat croppedFace = faceCropper.cropFace(matImage, detectedFace);
                    float[] embeddings = faceFeatureExtractor.extractFeatures(facePreprocessor.preprocessFace(croppedFace));
                    Image croppedImage = JavaFxImageConverter.toImage(croppedFace);
                    EmbeddedImage embeddedImage = new EmbeddedImage(croppedImage, embeddings);
                    images.add(embeddedImage);
                    renderImage(embeddedImage);
                }
            }
        });

        StateStorage.capturedImagesProperty.addListener((_,_,newImages) -> {
            ImagesSlot.setMinHeight(100);
            images.addAll(newImages);
            newImages.forEach(this::renderImage);
            updateDragAndDropRegionVisibility();
        });

        images.addListener((ListChangeListener<? super EmbeddedImage>) _ -> updateDragAndDropRegionVisibility());
    }

    private void updateDragAndDropRegionVisibility() {
        boolean isEmpty = images.isEmpty();
        DragAndDropRegion.setManaged(isEmpty);
        DragAndDropRegion.setVisible(isEmpty);
        if (isEmpty) {
            ImagesSlot.setMinHeight(0);
        }
    }

    public void renderImage(EmbeddedImage embeddedImage) {
        ImageView imageView = new ImageView(embeddedImage.image());
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        Rectangle clip = new Rectangle(100, 100);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imageView.setClip(clip);
        imageView.setCursor(Cursor.HAND);

        int index = images.size();
        imageView.setOnMouseClicked(_ -> {
            images.remove(index);
            ImagesSlot.getChildren().remove(imageView);
        });
        ImagesSlot.getChildren().add(imageView);
    }

    public List<EmbeddedImage> getImages() {
        return images;
    }
}