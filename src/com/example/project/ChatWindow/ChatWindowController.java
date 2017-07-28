package com.example.project.ChatWindow;

import com.example.project.Serializable.Message;
import com.example.project.SessionManager.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ChatWindowController {
    @FXML
    TextField input_field;
    @FXML
    TextArea text_field;
    @FXML
    Button send_button;

    private SessionManager sessionManager = SessionManager.getInstance();
    private String username;
    private String recipient;

    public void handleMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            sendMessage();
        }
    }

    public void handleKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            sendMessage();
        }
    }

    public void sendMessage() {
        String message = input_field.getText();
        if (message.length() == 0) return;
        Message outgoingMessage = new Message(username, recipient, message);
        sessionManager.addOutgoingMessage(outgoingMessage);
        text_field.appendText("\n");
        text_field.appendText(username + ": " + message);
        input_field.clear();
        input_field.requestFocus();
    }

    public void initData(String username, String recipient) {
        this.username = username;
        this.recipient = recipient;
    }

    public void appendText(String sender, String message) {
        text_field.appendText("\n");
        text_field.appendText(sender + ": " + message);
    }

}
