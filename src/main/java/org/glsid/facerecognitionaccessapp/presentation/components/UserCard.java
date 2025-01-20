package org.glsid.facerecognitionaccessapp.presentation.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.ToggleSwitch;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;

import java.net.URL;
import java.util.ResourceBundle;

public class UserCard extends Component implements Initializable {

    @FXML
    private ToggleSwitch activationSwitch;

    @FXML
    private Button deleteBtn;

    @FXML
    private Label fullNameLabel;

    @FXML
    private Label lastAttemptLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private FlowPane roomSlot;

    @FXML
    private VBox root;

    @FXML
    private Pane userImageSlot;

    @FXML
    private Button viewBtn;

    protected UserCard() {
        super(Views.USER_CARD);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
