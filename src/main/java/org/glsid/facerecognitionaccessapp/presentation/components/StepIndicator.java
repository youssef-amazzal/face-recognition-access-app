package org.glsid.facerecognitionaccessapp.presentation.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Icons;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class StepIndicator extends Component implements Initializable {
    private final Icons icon;
    private final int stepNumber;
    private final String stepTitle;

    @FXML private ToggleButton root;
    @FXML private FontIcon StepIcon;
    @FXML private Label StepNumber;
    @FXML private Label StepTitle;

    public StepIndicator(Icons icon, int stepNumber, String stepTitle) {
        super(Views.STEP_INDICATOR);

        this.icon = icon;
        this.stepNumber = stepNumber;
        this.stepTitle = stepTitle;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StepIcon.setIconLiteral(icon.toString());
        StepNumber.setText("Step " + stepNumber);
        StepTitle.setText(stepTitle);
    }
}
