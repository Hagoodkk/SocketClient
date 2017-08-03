package com.example.project.CreateAccountScreen;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateAccountScreenController {
    @FXML
    TextField username_input;
    @FXML
    PasswordField password_input;
    @FXML
    PasswordField password2_input;
    @FXML
    Button createAccount_button;

    public void initialize() {
        Platform.runLater(() -> username_input.requestFocus());
    }

    public void handleCreateAccountButtonAction(ActionEvent actionEvent) {
        String username = username_input.getText();
        String password = password_input.getText();
        String password2 = password2_input.getText();

        if (username != null && password != null && password2 != null) {
            if (password.equals(password2)) {
                System.out.println("Create Account!");
            }
        }
    }
}
