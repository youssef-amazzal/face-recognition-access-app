package org.glsid.facerecognitionaccessapp.presentation.components.forms;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.glsid.facerecognitionaccessapp.presentation.Constants.Views;
import org.glsid.facerecognitionaccessapp.presentation.components.Component;

public class UserInfoForm extends Component {
    @FXML
    private TextField FirstNameField;

    @FXML
    private TextField LastNameField;

    @FXML
    private TextArea NotesArea;

    @FXML
    private ChoiceBox<?> RolesChoiceBox;

    public UserInfoForm() {
        super(Views.USER_INFO_FORM);
    }

    public String getFirstName() {
        return FirstNameField.getText();
    }

    public String getLastName() {
        return LastNameField.getText();
    }

    public String getNotes() {
        return NotesArea.getText();
    }
}
