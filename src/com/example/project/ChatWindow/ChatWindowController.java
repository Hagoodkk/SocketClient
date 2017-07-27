package com.example.project.ChatWindow;

import com.example.project.SessionManager.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.Socket;

public class ChatWindowController {
    @FXML
    TextField input_field;
    @FXML
    TextArea text_field;
    @FXML
    Button send_button;

    SessionManager sessionManager = SessionManager.getInstance();
    private String messageRecipient;
    private String username;
    private Socket clientSocket = sessionManager.getClientSocket();

    private BufferedReader in;
    private PrintWriter out;

    public void initialize() {
        messageRecipient = sessionManager.getMessageRecipient();
        username = sessionManager.getUsername();
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    String line = in.readLine();
                    if (line == null) {
                        break;
                    }
                    System.out.println(line);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });
    }

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
        out.println(message);
        text_field.appendText("\n");
        text_field.appendText(username + ": " + message);
        if (clientSocket.isClosed()) System.out.println("Closed.");
        input_field.clear();
        input_field.requestFocus();
        try {
            System.out.println(in.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
