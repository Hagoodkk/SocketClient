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

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

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

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private Queue<String> messageQueue = new LinkedList<>();

    public void initialize() {
        messageRecipient = sessionManager.getMessageRecipient();
        username = sessionManager.getUsername();
        try {
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message(username, messageRecipient, "");
                if (messageQueue.isEmpty()) {
                    message.setNullMessage(true);
                } else {
                    message.setMessage(messageQueue.remove());
                }

                try {
                    oos.writeObject(message);
                    oos.flush();
                    Message incomingMessage = (Message) ois.readObject();
                    if (!incomingMessage.isNullMessage()) {
                        System.out.println(incomingMessage.getMessage());
                        getMessage(incomingMessage.getMessage());
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (ClassNotFoundException cnfe) {
                    cnfe.printStackTrace();
                }
            }
        }, 0, 250);
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
        text_field.appendText("\n");
        text_field.appendText(username + ": " + message);
        input_field.clear();
        input_field.requestFocus();
        messageQueue.add(message);
    }

    public void getMessage(String message) {
        text_field.appendText("\n");
        text_field.appendText(messageRecipient + ": " + message);
    }
}
