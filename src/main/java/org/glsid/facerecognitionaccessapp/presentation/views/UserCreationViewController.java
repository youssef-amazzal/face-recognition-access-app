package org.glsid.facerecognitionaccessapp.presentation.views;


import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
import org.glsid.facerecognitionaccessapp.presentation.router.RoutableView;
import org.glsid.facerecognitionaccessapp.presentation.components.StepIndicator;

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
        initSteps();

        activeStep.set(steps.getFirst());
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
        steps.add(new StepIndicator(Icons.MDI_FILE_DOCUMENT, 1, "User Information"));
        steps.add(new StepIndicator(Icons.MDI_FACE_RECOGNITION, 2, "Face Images"));
        steps.add(new StepIndicator(Icons.MDI_LOCK, 3, "Permissions"));

        List<Try<Node>> results = steps.stream().map(step -> Try.apply(step::getRoot)).toList();
        results.stream().filter(res -> res instanceof Failure<?>).forEach(result -> ((Failure<Node>) result).value().printStackTrace());
        results.stream()
                .filter(res -> res instanceof Success<Node>)
                .map(res -> ((Success<Node>) res).value())
                .forEach(step -> step.ifPresent(StepIndicatorsSlot.getChildren()::add));

        activeStep.addListener((_, oldStep, newStep) -> {
            if (oldStep != null) oldStep.setActive(false);
            newStep.setActive(true);
            ActiveStepTitle.setText(newStep.getStepTitle());
        });
    }

    private void toggleActivation(Node component, boolean active) {
        component.setVisible(active);
        component.setManaged(active);
    }

}
