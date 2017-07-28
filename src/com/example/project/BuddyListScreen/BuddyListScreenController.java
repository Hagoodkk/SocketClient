package com.example.project.BuddyListScreen;

import com.example.project.ChatWindow.ChatWindow;
import com.example.project.ChatWindow.ChatWindowController;
import com.example.project.Serializable.BuddyList;
import com.example.project.Serializable.Message;
import com.example.project.SessionManager.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class BuddyListScreenController {
    @FXML
    ListView buddyListView;

    @FXML
    Parent root;

    private SessionManager sessionManager = SessionManager.getInstance();
    private Socket clientSocket = sessionManager.getClientSocket();
    private String username = sessionManager.getUsername();

    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;

    @FXML
    public void initialize() {
        BuddyList buddyList = sessionManager.getBuddyList();
        ObservableList<String> items = FXCollections.observableArrayList(buddyList.getCurrentlyOnline());
        buddyListView.setItems(items);
        try {
            toServer = new ObjectOutputStream(clientSocket.getOutputStream());
            fromServer = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Message serverOutbound;
                if (sessionManager.getOutgoingQueue().isEmpty()) {
                    serverOutbound = new Message(true);
                } else {
                    serverOutbound = sessionManager.getOutgoingQueue().remove();
                }
                try {
                    toServer.writeObject(serverOutbound);
                    toServer.flush();
                    Message serverInbound = (Message) fromServer.readObject();
                    if (!serverInbound.isNullMessage()) {
                        System.out.println(serverInbound.getMessage());
                        ChatWindowController controller =
                                sessionManager.getChatWindowController(username, serverInbound.getSender());
                        if (controller != null) {
                            controller.appendText(serverInbound.getSender(), serverInbound.getMessage());
                        }
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (ClassNotFoundException cnfe) {
                    cnfe.printStackTrace();
                }
            }
        }, 0, 1);
    }

    public void handleMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                String recipient = buddyListView.getSelectionModel().getSelectedItem().toString();
                ChatWindow chatWindow = new ChatWindow();
                chatWindow.initData(username, recipient);
                try {
                    chatWindow.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
