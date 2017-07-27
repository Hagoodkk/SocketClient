package com.example.project.BuddyListScreen;

import com.example.project.ChatWindow.ChatWindow;
import com.example.project.Serializable.BuddyList;
import com.example.project.SessionManager.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class BuddyListScreenController {
    @FXML
    ListView buddyListView;

    private SessionManager sessionManager = SessionManager.getInstance();

    @FXML
    public void initialize() {
        BuddyList buddyList = sessionManager.getBuddyList();
        ObservableList<String> items = FXCollections.observableArrayList(buddyList.getCurrentlyOnline());
        buddyListView.setItems(items);
    }
    public void handleMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                sessionManager.setMessageRecipient(buddyListView.getSelectionModel().getSelectedItem().toString());
                ChatWindow chatWindow = new ChatWindow();
                try {
                    chatWindow.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
