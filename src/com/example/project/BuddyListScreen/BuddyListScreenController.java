package com.example.project.BuddyListScreen;

import com.example.project.ChatWindow.ChatWindow;
import com.example.project.ChatWindow.ChatWindowController;
import com.example.project.Serializable.Buddy;
import com.example.project.Serializable.BuddyList;
import com.example.project.Serializable.Message;
import com.example.project.SessionManager.SessionManager;
import com.example.project.WelcomeScreen.WelcomeScreenController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BuddyListScreenController {
    @FXML
    ListView buddyListView;
    @FXML
    ImageView buddylist_icon;
    @FXML
    HBox buddylist_icon_hbox;

    private Timer timer;

    private ObservableList<String> listViewItems;

    private SessionManager sessionManager = SessionManager.getInstance();
    private Socket clientSocket = sessionManager.getClientSocket();
    private String username = sessionManager.getUsername();
    private int ithSecond;

    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;

    @FXML
    public void initialize() {
        buddylist_icon.setImage(new Image("images/penguin1.png"));
        buddylist_icon_hbox.setAlignment(Pos.CENTER);

        Media media = new Media(getClass().getClassLoader().getResource("sounds/395798__lipsumdolor__computer-startup.wav").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        buildBuddyList(sessionManager.getBuddyList());
        try {
            toServer = new ObjectOutputStream(clientSocket.getOutputStream());
            fromServer = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        int timerInterval = 50;
        ithSecond = 0;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                Message serverOutbound;
                if (sessionManager.getOutgoingQueue().isEmpty()) {
                    serverOutbound = new Message(true);
                } else {
                    serverOutbound = sessionManager.getOutgoingQueue().remove();
                }
                if (ithSecond == (timerInterval / 4)) {
                    serverOutbound.setBuddyListUpdate(true);
                    serverOutbound.setBuddyList(sessionManager.getBuddyList());
                    ithSecond = 0;
                } else ithSecond++;

                try {
                    toServer.writeObject(serverOutbound);
                    toServer.flush();
                    Message serverInbound = (Message) fromServer.readObject();
                    if (serverInbound.isBuddyListUpdate()) {
                        updateBuddyList(serverInbound.getBuddyList());
                    }
                    if (!serverInbound.isNullMessage()) {
                        System.out.println(serverInbound.getMessage());
                        ChatWindowController controller =
                                sessionManager.getChatWindowController(username, serverInbound.getSender());
                        if (controller != null) {
                            controller.appendText(serverInbound.getSender(), serverInbound.getMessage());
                        } else {
                            ChatWindow chatWindow = new ChatWindow();
                            chatWindow.initData(username, serverInbound.getSender());
                            try {
                                chatWindow.start();
                                ChatWindowController controller2 =
                                        sessionManager.getChatWindowController(username, serverInbound.getSender());
                                controller2.appendText(serverInbound.getSender(), serverInbound.getMessage());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (ClassNotFoundException cnfe) {
                    cnfe.printStackTrace();
                }
            });
            }
        }, 0, timerInterval);
    }

    private void buildBuddyList(BuddyList buddyList) {
        ArrayList<String> currentlyOnline = new ArrayList<>();
        for (Buddy buddy : buddyList.getCurrentlyOnline()) {
            currentlyOnline.add(buddy.getDisplayName());
        }

        listViewItems = FXCollections.observableArrayList(currentlyOnline);
        buddyListView.setItems(listViewItems);
    }

    private void updateBuddyList(BuddyList buddyList) {
        String currentlySelected = null;
        if (!buddyListView.getSelectionModel().isEmpty()) {
            currentlySelected = buddyListView.getSelectionModel().getSelectedItem().toString();
        }
        listViewItems.clear();
        buddyListView.getItems().clear();
        for (Buddy buddy : buddyList.getCurrentlyOnline()) {
            listViewItems.add(buddy.getDisplayName());
        }
        buddyListView.setItems(listViewItems);
        if (currentlySelected != null && listViewItems.contains(currentlySelected)) {
            buddyListView.getSelectionModel().select(currentlySelected);
        }
    }

    public void handleMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                String recipient = buddyListView.getSelectionModel().getSelectedItem().toString();
                ChatWindowController chatWindowController = sessionManager.getChatWindowController(username, recipient);
                if (chatWindowController != null) {
                    chatWindowController.requestFocus();
                } else {
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

    public void shutdown() {
        timer.cancel();
        WelcomeScreenController welcomeScreenController = sessionManager.getWelcomeScreenController();
        sessionManager.closeAllChatWindows();
        welcomeScreenController.showStage();
        sessionManager.nullify();
    }

}
