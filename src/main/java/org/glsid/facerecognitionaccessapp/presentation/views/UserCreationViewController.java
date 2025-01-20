package org.glsid.facerecognitionaccessapp.presentation.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.glsid.facerecognitionaccessapp.core.exceptions.Failure;
import org.glsid.facerecognitionaccessapp.core.exceptions.Success;
import org.glsid.facerecognitionaccessapp.core.exceptions.Try;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Icons;
import org.glsid.facerecognitionaccessapp.presentation.components.StepIndicator;
import org.glsid.facerecognitionaccessapp.presentation.router.RoutableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserCreationViewController extends RoutableView implements Initializable {

    @FXML private VBox root;
    @FXML private Button DoneButton;
    @FXML private Button NextButton;
    @FXML private Button PreviousButton;
    @FXML private Label ActiveStepTitle;
    @FXML private VBox StepContentSlot;
    @FXML private VBox StepsSlot;

    private final List<StepIndicator> steps = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSteps();
        DoneButton.setManaged(false);
    }

    void initSteps() {
        steps.add(new StepIndicator(Icons.MDI_FILE_DOCUMENT, 1, "User Information"));
        steps.add(new StepIndicator(Icons.MDI_FACE_RECOGNITION, 2, "Face Images"));
        steps.add(new StepIndicator(Icons.MDI_LOCK, 3, "Permissions"));

        List<Try<Node>> results = steps.stream().map(step -> Try.apply(step::getRoot)).toList();
        results.stream().filter(res -> res instanceof Failure<?>).forEach(result -> ((Failure<Node>) result).value().printStackTrace());
        results.stream()
                .filter(res -> res instanceof Success<Node>)
                .map(res -> ((Success<Node>) res).value())
                .forEach(step -> step.ifPresent(StepsSlot.getChildren()::add));
    }
}
