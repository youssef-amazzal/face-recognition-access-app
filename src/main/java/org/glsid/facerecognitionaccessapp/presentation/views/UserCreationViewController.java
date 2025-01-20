package org.glsid.facerecognitionaccessapp.presentation.views;


import javafx.beans.Observable;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.glsid.facerecognitionaccessapp.core.exceptions.Try;
import org.glsid.facerecognitionaccessapp.core.exceptions.Failure;
import org.glsid.facerecognitionaccessapp.core.exceptions.Success;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Icons;
import org.glsid.facerecognitionaccessapp.presentation.components.forms.UserImageForm;
import org.glsid.facerecognitionaccessapp.presentation.components.forms.UserInfoForm;
import org.glsid.facerecognitionaccessapp.presentation.router.MainRouteData;
import org.glsid.facerecognitionaccessapp.presentation.router.RoutableView;
import org.glsid.facerecognitionaccessapp.presentation.components.StepIndicator;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserCreationViewController extends RoutableView implements Initializable {

    @FXML private VBox root;
    @FXML private Button DoneButton;
    @FXML private Button NextButton;
    @FXML private Button PreviousButton;
    @FXML private Label ActiveStepTitle;
    @FXML private VBox StepContentSlot;
    @FXML private VBox StepIndicatorsSlot;

    private final List<StepIndicator> steps = new ArrayList<>();
    private final ObjectProperty<StepIndicator> activeStep = new SimpleObjectProperty<>();
    private final SimpleListProperty<StepIndicator> history = new SimpleListProperty<>(FXCollections.observableArrayList());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initNextButton();
        initPreviousButton();
        initDoneButton();

        ChangeListener<Scene> sceneChangeListener = new ChangeListener<>() {

            @Override
            public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
                if (newValue == null) return;
                initSteps();
                activeStep.set(steps.getFirst());
                root.sceneProperty().removeListener(this);
            }
        };

        root.sceneProperty().addListener(sceneChangeListener);
    }

    private void initPreviousButton() {
        activeStep.addListener((_, _, newValue) -> toggleActivation(PreviousButton, !newValue.equals(steps.getFirst())));
        PreviousButton.setOnAction(_ -> {
            activeStep.set(history.getLast());
            history.removeLast();
            activeStep.get().setDone(false);
        });
    }

    private void initNextButton() {
        activeStep.addListener((_, _, newValue) -> toggleActivation(NextButton, !newValue.equals(steps.getLast())));
        NextButton.setOnAction(_ -> {
            history.add(activeStep.get());
            activeStep.get().setDone(true);
            activeStep.set(steps.get(steps.indexOf(activeStep.get()) + 1));
        });
    }

    private void initDoneButton() {
        activeStep.addListener((_, _, newValue) -> toggleActivation(DoneButton, newValue.equals(steps.getLast())));
        DoneButton.setOnAction(_ -> {
            history.add(activeStep.get());
            activeStep.get().setDone(true);
            getRouter().pop();
        });
    }

    private void initSteps() {
        steps.add(new StepIndicator(Icons.MDI_FILE_DOCUMENT, 1, "User Information", new UserInfoForm()));
        steps.add(new StepIndicator(Icons.MDI_FACE_RECOGNITION, 2, "Face Images", new UserImageForm((MainRouteData) getRouteData())));
        steps.add(new StepIndicator(Icons.MDI_LOCK, 3, "Permissions", new UserInfoForm()));

        List<Try<Node>> results = steps.stream().map(step -> Try.apply(step::getRoot)).toList();
        results.stream().filter(res -> res instanceof Failure<?>).forEach(result -> ((Failure<Node>) result).value().printStackTrace());
        results.stream()
                .filter(res -> res instanceof Success<Node>)
                .map(res -> ((Success<Node>) res).value())
                .forEach(step -> step.ifPresent(StepIndicatorsSlot.getChildren()::add));

        activeStep.addListener((_, oldStep, newStep) -> {
            try {
                if (oldStep != null) oldStep.setActive(false);
                newStep.setActive(true);
                ActiveStepTitle.setText(newStep.getStepTitle());
                StepContentSlot.getChildren().setAll(newStep.getContent().getRoot());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void toggleActivation(Node component, boolean active) {
        component.setVisible(active);
        component.setManaged(active);
    }

}
