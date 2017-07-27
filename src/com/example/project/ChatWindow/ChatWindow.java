package com.example.project.ChatWindow;

import com.example.project.SessionManager.SessionManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatWindow {
    private SessionManager sessionManager = SessionManager.getInstance();

    public void start() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ChatWindow.fxml"));
        Stage buddyListStage = new Stage();
        String username = sessionManager.getUsername();
        String messageRecipient = sessionManager.getMessageRecipient();
        buddyListStage.setTitle(username + " -> " + messageRecipient);
        buddyListStage.setScene(new Scene(root, 502, 344));
        buddyListStage.show();
    }


}
