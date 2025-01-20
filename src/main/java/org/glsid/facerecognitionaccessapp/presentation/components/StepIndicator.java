package org.glsid.facerecognitionaccessapp.presentation.components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Icons;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Styles;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class StepIndicator extends Component implements Initializable {
    private final Icons icon;
    private final int stepNumber;
    private final String stepTitle;
    private final BooleanProperty done = new SimpleBooleanProperty(false);
    private final BooleanProperty active = new SimpleBooleanProperty(false);
    private final PseudoClass DONE = PseudoClass.getPseudoClass("done");
    private final PseudoClass ACTIVE = PseudoClass.getPseudoClass("active");

    @FXML private HBox root;
    @FXML private FontIcon StepIcon;
    @FXML private Label StepNumber;
    @FXML private Label StepTitle;


    public StepIndicator(Icons icon, int stepNumber, String stepTitle) {
        super(Views.STEP_INDICATOR, Styles.STEP_INDICATOR);

        this.icon = icon;
        this.stepNumber = stepNumber;
        this.stepTitle = stepTitle;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StepIcon.setIconLiteral(icon.toString());
        StepNumber.setText("Step " + stepNumber);
        StepTitle.setText(stepTitle);

        active.addListener((_, _, newValue) -> root.pseudoClassStateChanged(ACTIVE, newValue));
        done.addListener((_, _, newValue) -> {
            root.pseudoClassStateChanged(DONE, newValue);
            StepIcon.setIconLiteral(newValue ? Icons.MDI_CHECK_CIRCLE.toString() : icon.toString());
        });
    }

    public String getStepTitle() {
        return stepTitle;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public boolean isDone() {
        return done.get();
    }

    public BooleanProperty doneProperty() {
        return done;
    }

    public void setDone(boolean done) {
        this.done.set(done);
    }

    public boolean isActive() {
        return active.get();
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

}
