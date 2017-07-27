package com.example.project.SessionManager;

import com.example.project.Serializable.BuddyList;

import java.net.Socket;

public class SessionManager {
    private static SessionManager sessionManager;

    private String username;
    private Socket clientSocket;
    private BuddyList buddyList;
    private String messageRecipient;

    public String getMessageRecipient() {
        return messageRecipient;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setMessageRecipient(String messageRecipient) {
        this.messageRecipient = messageRecipient;
    }

    private SessionManager() {}

    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }
    public void setBuddyList(BuddyList buddyList) { this.buddyList = buddyList; }
    public BuddyList getBuddyList() { return this.buddyList; }

    public static SessionManager getInstance() {
        if (sessionManager == null) {
            sessionManager = new SessionManager();
        }
        return sessionManager;
    }
}
