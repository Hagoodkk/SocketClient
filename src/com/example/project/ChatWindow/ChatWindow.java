package com.example.project.ChatWindow;

import com.example.project.SessionManager.SessionManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatWindow {
    private SessionManager sessionManager = SessionManager.getInstance();

    private String username;
    private String recipient;

    public void start() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatWindow.fxml"));
        Parent root = loader.load();
        ChatWindowController controller = loader.getController();
        sessionManager.addChatWindowController(username, recipient, controller);
        controller.initData(username, recipient);
        Stage buddyListStage = new Stage();
        buddyListStage.setTitle(username + " -> " + recipient);
        buddyListStage.setScene(new Scene(root, 502, 344));
        buddyListStage.show();
    }

    public void initData(String username, String recipient) {
        this.username = username;
        this.recipient = recipient;
    }
}
